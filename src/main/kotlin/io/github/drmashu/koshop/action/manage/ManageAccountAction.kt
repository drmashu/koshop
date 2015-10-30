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
class ManageAccountAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val accountDao: AccountDao, val id: Int?): HtmlAction(context, request, response) {
    override fun get() {
        logger.entry()
        domaConfig.transactionManager.required {
            val account = accountDao.selectById(id?:0)
            responseFromTemplate("/manage/success_account", arrayOf(object {
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
            accountDao.insert(account)

            var scratch:IntArray? = null
            var qrcode:String? = null
            if (account.gauth?:false) {
                val gAuth = GoogleAuthenticator()
                val key = gAuth.createCredentials(account.mail)
                val totp = Totp()
                totp.id = account.id
                totp.key = key.key
                scratch = key.scratchCodes.toIntArray()
                qrcode = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("koshop", account.mail, key)
            }
            responseFromTemplate("/manage/success_account", arrayOf(object {
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
            responseFromTemplate("/manage/success_account", arrayOf(object {
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
            val account = accountDao.selectById(id!!)
            accountDao.delete(account)
        }
        logger.exit()
    }

    /**
     *
     */
    private fun getAccount(): Account {
        logger.entry()
        val reader = request.getPart("account").inputStream.bufferedReader("UTF-8")
        var data = ""
        while(reader.ready()) {
            data += reader.readLine()
        }
        logger.trace(data)
        val result =  objectMapper.readValue(data, Account::class.java)
        logger.exit(result)
        return result
    }
}