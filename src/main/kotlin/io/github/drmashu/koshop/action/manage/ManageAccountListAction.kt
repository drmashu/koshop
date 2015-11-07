package io.github.drmashu.koshop.action.manage

import com.warrenstrange.googleauth.GoogleAuthenticator
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator
import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.dikon.inject
import io.github.drmashu.koshop.dao.AccountDao
import io.github.drmashu.koshop.dao.getNextId
import io.github.drmashu.koshop.model.Account
import io.github.drmashu.koshop.model.Totp
import org.seasar.doma.jdbc.Config
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/24.
 */
class ManageAccountListAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val accountDao: AccountDao): HtmlAction(context, request, response) {
    val defaultAccount = Account()
    init {
        defaultAccount.id = 0
        defaultAccount.name = ""
        defaultAccount.mail = ""
        defaultAccount.gauth = true
    }
    override fun get() {
        logger.entry()
        domaConfig.transactionManager.required {
            val accountList = accountDao.selectAll()
            responseFromTemplate("/manage/account_list", arrayOf(object {
                val accountList = accountList
            }))
        }
        logger.exit()
    }
}