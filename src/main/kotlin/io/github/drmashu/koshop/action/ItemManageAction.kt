package io.github.drmashu.koshop.action

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.dikon.inject
import io.github.drmashu.koshop.dao.ItemDao
import io.github.drmashu.koshop.dao.ItemImageDao
import io.github.drmashu.koshop.dao.getNextId
import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.Item
import io.github.drmashu.koshop.model.ItemImage
import org.apache.logging.log4j.LogManager
import org.seasar.doma.jdbc.Config
import java.sql.Blob
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.sql.rowset.serial.SerialBlob
import kotlin.text.Regex

/**
 * Created by drmashu on 2015/10/17.
 */

public class ItemManageAction(request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val itemDao: ItemDao, val itemImageDao: ItemImageDao, val id: String?): HtmlAction(request, response) {
    companion object{
        val logger = LogManager.getLogger(ItemAction::class.java)
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
    }
    init {
        logger.entry(request, response, itemDao, id)
    }

    /**
     *
     */
    override fun get() {
        logger.entry()
        val itemId = id!!
        val item = itemDao.selectById(itemId)
        item.images = itemImageDao.selectByItemIdWithoutBlob(itemId)
        responseByJson(item)
        logger.exit()
    }

    /**
     *
     */
    override fun post() {
        logger.entry()
        domaConfig.transactionManager.required {
            val id = itemDao.getNextId()
            val item = getItem()
            item.id = id
            itemDao.insert(item)
//            for(part in request.parts) {
//                val itemImg = ItemImage()
//                itemImg.itemId = item.id
//                itemImg.index = Regex("images\\[([0-9]+)]").match(part.name)!!.groups[1]!!.value.toInt() as Byte
//                itemImg.contentType = part.contentType
//                val buf = part.inputStream.readBytes()
//                val image = SerialBlob(buf)
//                itemImg.image = image
//                itemImageDao.insert(itemImg)
//            }
            responseByJson(item)
        }
        logger.exit()
    }

    /**
     *
     */
    private fun getItem(): Item {
        logger.entry()
        val data = request.getParameter("item")
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
        domaConfig.transactionManager.required {
            val item = getItem()
            itemDao.update(item)
            for(part in request.parts) {
                val itemImg = ItemImage()
                itemImg.itemId = item.id
                itemImg.index = Regex("images\\[([0-9]+)]").match(part.name)!!.groups[1]!!.value.toInt() as Byte
                itemImg.contentType = part.contentType
                val buf = part.inputStream.readBytes()
                val image = SerialBlob(buf)
                itemImg.image = image
                itemImageDao.insert(itemImg)
            }
            responseByJson(item)
        }
        logger.exit()
    }

    /**
     *
     */
    override fun delete() {
        logger.entry()
        domaConfig.transactionManager.required {
            val item = getItem()
            itemDao.delete(item)
            responseByJson(item)
        }
        logger.exit()
    }
}