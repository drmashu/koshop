package io.github.drmashu.koshop.action.manage

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.dikon.inject
import io.github.drmashu.koshop.action.ItemAction
import io.github.drmashu.koshop.dao.ItemDao
import io.github.drmashu.koshop.dao.ItemImageDao
import io.github.drmashu.koshop.dao.getNextId
import io.github.drmashu.koshop.model.Item
import io.github.drmashu.koshop.model.ItemImage
import org.apache.logging.log4j.LogManager
import org.seasar.doma.jdbc.Config
import org.seasar.doma.jdbc.tx.TransactionManager
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.Part
import javax.sql.rowset.serial.SerialBlob
import kotlin.text.Regex

/**
 * Created by drmashu on 2015/10/17.
 */

public class ManageItemAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val itemDao: ItemDao, val itemImageDao: ItemImageDao, val id: String?): HtmlAction(context, request, response) {
    companion object{
        val logger = LogManager.getLogger(ItemAction::class.java)
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
    }
    val transactionManager: TransactionManager
    init {
        logger.entry(request, response, itemDao, id)
        transactionManager = domaConfig.transactionManager
    }

    /**
     *
     */
    override fun get() {
        logger.entry()
        val itemId = id!!
        val item = itemDao.selectById(itemId)
        item.images = itemImageDao.selectByItemIdWithoutBlob(itemId).toArrayList()
        responseByJson(item)
        logger.exit()
    }

    /**
     *
     */
    override fun post() {
        logger.entry()
        transactionManager.required {
            val id = itemDao.getNextId()
            val item = getItem()
            item.id = id
            itemDao.insert(item)
            insertItemImages(item, request.parts)
            responseByJson(item)
        }
        logger.exit()
    }

    /**
     *
     */
    private fun getItem(): Item {
        logger.entry()
        val reader = request.getPart("item").inputStream.bufferedReader("UTF-8")
        var data = ""
        while(reader.ready()) {
            data += reader.readLine()
        }
        logger.trace(data)
        val result =  objectMapper.readValue(data, Item::class.java)
        logger.exit(result)
        return result
    }

    /**
     *
     */
    override fun put() {
        logger.entry()
        transactionManager.required {
            val item = getItem()
            itemDao.update(item)
            insertItemImages(item, request.parts)
            responseByJson(item)
        }
        logger.exit()
    }

    private fun insertItemImages(item: Item, parts: MutableCollection<Part>) {
        for (part in parts) {
            logger.trace(part.name)
            insertItemImage(item, part)
        }
    }

    private fun insertItemImage(item: Item, part: Part) {
        val itemImg = ItemImage()
        itemImg.itemId = item.id
        val matched = Regex("images\\[([0-9]+)\\]").matchEntire(part.name)
        if (matched != null) {
            itemImg.index = matched.groups[1]!!.value.toInt() as Byte
            itemImg.contentType = part.contentType
            val buf = part.inputStream.readBytes()
            val image = SerialBlob(buf)
            itemImg.image = image
            itemImageDao.insert(itemImg)
        }
    }

    /**
     *
     */
    override fun delete() {
        logger.entry()
        transactionManager.required {
            val item = getItem()
            itemDao.delete(item)
            responseByJson(item)
        }
        logger.exit()
    }
}