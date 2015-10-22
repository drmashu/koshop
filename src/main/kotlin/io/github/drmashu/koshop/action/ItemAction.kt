package io.github.drmashu.koshop.action

import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.koshop.dao.ItemDao
import io.github.drmashu.koshop.dao.ItemImageDao
import org.apache.logging.log4j.LogManager
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/17.
 */
public open class ItemAction(request: HttpServletRequest, response: HttpServletResponse, val itemDao: ItemDao, val itemImgDao: ItemImageDao, val id: String): HtmlAction(request, response) {
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
        logger.entry()
        val itemId = id
        val item = itemDao.selectById(itemId)
        item.images = itemImgDao.selectByItemIdWithoutBlob(itemId)

        responseByJson(item)
        logger.exit()
    }
}