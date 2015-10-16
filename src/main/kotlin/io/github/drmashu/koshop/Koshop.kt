package io.github.drmashu.koshop

import io.github.drmashu.buri.Buri
import io.github.drmashu.buri.BuriRunner
import io.github.drmashu.dikon.*
import io.github.drmashu.koshop.action.SearchAction
import org.apache.logging.log4j.LogManager
import org.seasar.doma.jdbc.Config
import org.seasar.doma.jdbc.dialect.Dialect
import org.seasar.doma.jdbc.dialect.H2Dialect
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource
import javax.sql.DataSource

val mainLogger = LogManager.getLogger("KoshopMain")
/**
 * Created by drmashu on 2015/10/07.
 */
fun main(args: Array<String>){
    mainLogger.entry()
    BuriRunner(Koshop()).start(8080)
    mainLogger.exit()
}

class Koshop() : Buri() {
    init {

    }
    val datasource = LocalTransactionDataSource("jdbc:h2:koshop", "sa", "")
    val diarect = H2Dialect()
    override val config: Map<String, Factory<*>>
        get() = mapOf(
                "doma_config" to Holder(object: Config {
                    override fun getDataSource(): DataSource? = datasource
                    override fun getDialect(): Dialect? = diarect
                }),
                "itemDao" to Injection(getKClassForName("io.github.drmashu.koshop.dao.ItemDaoImpl")),
                "accountDao" to Injection(getKClassForName("io.github.drmashu.koshop.dao.AccountDaoImpl")),
                "title" to Holder("Koshop"),
                "/" to Injection(pages.index::class),
                "/item/(?<id>[A-Za-z0-9]+)" to Injection(pages.item::class),
                "/search/(?<keyword>.*)" to Injection(SearchAction::class)
        )
}
