package io.github.drmashu.koshop.action

import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.koshop.dao.AccountDao
import org.apache.logging.log4j.LogManager
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/10.
 */
class SearchAction(request: HttpServletRequest, response: HttpServletResponse, val accountDao: AccountDao, val keyword: String):HtmlAction(request, response) {
    companion object{
        val logger = LogManager.getLogger(SearchAction::class)
    }
    override fun post() {
        val items = accountDao.selectAll()
        redirect("/item/${items[0].id}}")
    }
}