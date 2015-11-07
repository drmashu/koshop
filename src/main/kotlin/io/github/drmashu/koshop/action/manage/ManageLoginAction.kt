package io.github.drmashu.koshop.action.manage

import com.warrenstrange.googleauth.GoogleAuthenticator
import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.dikon.inject
import io.github.drmashu.koshop.dao.AccountDao
import io.github.drmashu.koshop.dao.TotpDao
import io.github.drmashu.koshop.model.Account
import io.github.drmashu.koshop.model.Role
import org.seasar.doma.jdbc.Config
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/23.
 */
class ManageLoginAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val accountDao: AccountDao, val totpDao: TotpDao): HtmlAction(context, request, response) {
    override fun get() {
        domaConfig.transactionManager.required {
            if (accountDao.countByRole(Role.ADMINISTRATOR) == 0) {
                val account = Account()
                account.id = 0
                account.name = ""
                account.password = ""
                account.gauth = false
                account.role = Role.ADMINISTRATOR
                responseFromTemplate("/manage/account", arrayOf(account))
            } else {
                responseFromTemplate("/manage/login")
            }
        }
    }

    override fun post() {
        val id = request.getParameter("id")
        val password = request.getParameter("password")
        val account = accountDao.selectById(id.toInt())
        if (account.password == password) {
            if(account.gauth?:false) {
                responseFromTemplate("/manage/login2nd", arrayOf(object {val id = id}))
            } else {
                request.session.setAttribute("auth", id)
                responseFromTemplate("/manage/items")
            }
        } else {
            responseFromTemplate("/manage/login")
        }
    }

    override fun put() {
        val id = request.getParameter("id").toInt()
        val passcode = request.getParameter("passcode").toInt()
        val gAuth = GoogleAuthenticator()
        val secureKey = totpDao.selectById(id)!!.key
        if (gAuth.authorize(secureKey, passcode)) {
            responseFromTemplate("/manage/items")
        } else {
            responseFromTemplate("/manage/login2nd")
        }
    }
}