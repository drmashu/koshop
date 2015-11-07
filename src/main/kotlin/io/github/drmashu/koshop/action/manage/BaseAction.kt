package io.github.drmashu.koshop.action.manage

import io.github.drmashu.buri.HtmlAction
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/11/02.
 */
open class BaseAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse) : HtmlAction(context, request, response) {
    override fun responseFromTemplate(fileName: String, objs: Array<Any>?) {
        super.responseFromTemplate(fileName, objs)
    }
}