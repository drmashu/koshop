package io.github.drmashu.koshop.action

import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.koshop.dao.ItemDao
import org.apache.logging.log4j.LogManager
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/10.
 */
public class SearchAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, val itemDao: ItemDao, val keyword: String):HtmlAction(context, request, response) {
    companion object{
        val logger = LogManager.getLogger(SearchAction::class.java)
    }
    init {
        logger.entry(request, response, itemDao, keyword)
    }

    /**
     *
     */
    override fun get() {
        logger.entry()
        val items = itemDao.selectAll()

        logger.exit()
    }
}