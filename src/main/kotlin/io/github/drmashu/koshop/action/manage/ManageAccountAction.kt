package io.github.drmashu.koshop.action.manage

import com.warrenstrange.googleauth.GoogleAuthenticator
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator
import io.github.drmashu.dikon.inject
import io.github.drmashu.koshop.dao.AccountDao
import io.github.drmashu.koshop.dao.TotpDao
import io.github.drmashu.koshop.dao.getNextId
import io.github.drmashu.koshop.model.Account
import io.github.drmashu.koshop.model.Role
import io.github.drmashu.koshop.model.Totp
import org.seasar.doma.jdbc.Config
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/24.
 */
class ManageAccountAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val accountDao: AccountDao, val totpDao: TotpDao, val id: String?): BaseAction(context, request, response) {
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
            val account = if(id == null || id == "0") defaultAccount else accountDao.selectById(id.toInt())
            responseFromTemplate("/manage/account", arrayOf(object {
                val id = account.id
                val name = account.name
                val mail = account.mail
                val gauth = account.gauth

                val scratch = null
                val qrcode = null
            }))
        }
        logger.exit()
    }

    override fun post() {
        logger.entry()
        domaConfig.transactionManager.required {
            val account = getAccount()
            account.id = accountDao.getNextId()
            val oldAccount = accountDao.selectByMail(account.mail!!)
            if (oldAccount != null) {
                // TODO エラーメッセージ
            }
            accountDao.insert(account)

            var scratch:IntArray? = null
            var qrcode:String? = null
            if (account.gauth?:false) {
                val gAuth = GoogleAuthenticator()
                val key = gAuth.createCredentials()
                val totp = Totp()
                totp.id = account.id
                totp.key = key.key
                scratch = key.scratchCodes.toIntArray()
                qrcode = GoogleAuthenticatorQRGenerator.getOtpAuthURL("koshop", account.mail, key)
                totpDao.insert(totp)
            }
            logger.trace(qrcode)
            responseFromTemplate("/manage/account_confirm", arrayOf(object {
                val id = account.id
                val name = account.name
                val mail = account.mail
                val gauth = account.gauth

                val scratch = scratch
                val qrcode = qrcode
            }))
        }
        logger.exit()
    }

    override fun put() {
        logger.entry()
        domaConfig.transactionManager.required {
            val account = getAccount()
            accountDao.update(account)
            responseFromTemplate("/manage/account_confirm", arrayOf(object {
                val id = account.id
                val name = account.name
                val mail = account.mail
                val gauth = account.gauth

                val scratch = null
                val qrcode = null
            }))
        }
        logger.exit()
    }

    override fun delete() {
        logger.entry()
        domaConfig.transactionManager.required {
            val account = accountDao.selectById(id!!.toInt())
            accountDao.delete(account)
        }
        logger.exit()
    }

    /**
     *
     */
    private fun getAccount(): Account {
        logger.entry()
        val result = Account()
        var id = request.getParameter("id")
        id = if (id.isNullOrEmpty()) "0" else id
        result.id = id.toInt()
        result.mail = request.getParameter("mail")
        result.name = request.getParameter("name")
        result.password = request.getParameter("password")
        result.gauth = request.getParameter("gauth") == "true"
        result.role = if (result.id == 0) Role.ADMINISTRATOR else Role.MANAGER
        logger.exit(result)
        return result
    }
}