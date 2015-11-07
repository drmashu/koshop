package io.github.drmashu.koshop.action.manage

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.drmashu.buri.HtmlAction
import io.github.drmashu.dikon.inject
import io.github.drmashu.koshop.action.ItemAction
import io.github.drmashu.koshop.dao.ItemDao
import io.github.drmashu.koshop.dao.ItemImageDao
import org.apache.logging.log4j.LogManager
import org.seasar.doma.jdbc.Config
import org.seasar.doma.jdbc.tx.TransactionManager
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by drmashu on 2015/10/17.
 */

public class ItemListAction(context: ServletContext, request: HttpServletRequest, response: HttpServletResponse, @inject("doma_config") val domaConfig: Config, val itemDao: ItemDao, val itemImageDao: ItemImageDao): HtmlAction(context, request, response) {
    companion object{
        val logger = LogManager.getLogger(ItemAction::class.java)
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
    }
    val transactionManager: TransactionManager
    init {
        logger.entry(request, response, itemDao)
        transactionManager = domaConfig.transactionManager
    }

    /**
     *
     */
    override fun get() {
        logger.entry()
        val items = itemDao.selectAll()
        responseFromTemplate("/items", arrayOf({val itemList =  items}))
        logger.exit()
    }
}