package io.github.drmashu.koshop.action

import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.koshop.dao.ItemDao
import io.github.drmashu.koshop.dao.ItemImageDao
import io.github.drmashu.koshop.model.Item
import io.github.drmashu.koshop.model.ItemId
import org.apache.logging.log4j.LogManager
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/17.
 */
public class ItemManageAction(request: HttpServletRequest, response: HttpServletResponse, val itemDao: ItemDao, val itemImageDao: ItemImageDao, val id: String?): HtmlAction(request, response) {
    companion object{
        val logger = LogManager.getLogger(ItemAction::class.java)
    }
    init {
        logger.entry(request, response, itemDao, id)
    }

    /**
     *
     */
    override fun get() {
        val itemId = ItemId(id!!)
        val item = itemDao.selectById(itemId)
        item.images = itemImageDao.selectByItemIdWithoutBlob(itemId)
        responseByJson(item)
    }

    /**
     *
     */
    override fun post() {
    }

    /**
     *
     */
    override fun put() {

    }

    /**
     *
     */
    override fun delete() {

    }
}