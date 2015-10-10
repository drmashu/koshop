package io.github.drmashu.koshop.action

import io.github.drmashu.buri.HtmlAction
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/10.
 */
class SearchAction(request: HttpServletRequest, response: HttpServletResponse, val keyword: String):HtmlAction(request, response) {
    override fun post() {
        val id = ""
        redirect("/item/${id}}")
    }
}