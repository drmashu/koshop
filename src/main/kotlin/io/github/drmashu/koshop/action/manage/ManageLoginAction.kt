package io.github.drmashu.koshop.action.manage

import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.dikon.inject
import io.github.drmashu.koshop.dao.AccountDao
import io.github.drmashu.koshop.model.Role
import org.seasar.doma.jdbc.Config
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/23.
 */
class ManageLoginAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val accountDao: AccountDao): HtmlAction(context, request, response) {
    override fun get() {
        domaConfig.transactionManager.required {
            if (accountDao.countByRole(Role.ADMINISTRATOR) == 0) {
                responseFromFile("./mst/manage/input_account.mustache")
            } else {
                responseFromFile("./mst/manage/login.mustache")
            }
        }
    }
}