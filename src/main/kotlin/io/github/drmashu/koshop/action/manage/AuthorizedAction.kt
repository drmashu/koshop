package io.github.drmashu.koshop.action.manage

import io.github.drmashu.buri.HtmlAction
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/11/02.
 */
open class AuthorizedAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse) : BaseAction(context, request, response) {
    override fun isAuthenticated(): Boolean {
        val session = request.getSession(false)
        if (session != null) {
            val id = session.getAttribute("auth")
            if (id != null) {
                return true
            }
        }
        return false
    }
}