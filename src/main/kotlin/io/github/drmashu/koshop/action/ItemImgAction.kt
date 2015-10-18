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
public open class ItemImgAction(request: HttpServletRequest, response: HttpServletResponse, val itemImgDao: ItemImageDao, val id: String, val index:Int): HtmlAction(request, response) {
    companion object{
        val logger = LogManager.getLogger(ItemImgAction::class.java)
    }
    init {
        logger.entry(request, response, itemImgDao, id)
    }

    /**
     *
     */
    override fun get() {
        logger.entry()
        val item = itemImgDao.selectById(ItemId(id), index)
        responseByJson(item)
        logger.exit()
    }
}