package io.github.drmashu.koshop.action.manage

import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.dikon.inject
import io.github.drmashu.koshop.dao.AccountDao
import org.seasar.doma.jdbc.Config
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/24.
 */
class ManageAccountAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val accountDao: AccountDao, val id: Int?): HtmlAction(context, request, response) {
    override fun get() {
        domaConfig.transactionManager.required {
            responseByJson(accountDao.selectById(id?:0))
        }
    }

    override fun post() {

    }

    override fun put() {

    }

    override fun delete() {

    }
}