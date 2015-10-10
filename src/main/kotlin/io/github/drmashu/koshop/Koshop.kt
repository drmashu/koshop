package io.github.drmashu.koshop

import io.github.drmashu.buri.Buri
import io.github.drmashu.buri.BuriRunner
import io.github.drmashu.dikon.Factory
import io.github.drmashu.dikon.Holder
import io.github.drmashu.dikon.Injection
import io.github.drmashu.koshop.action.SearchAction
import io.github.drmashu.koshop.dao.*
import org.seasar.doma.jdbc.Config
import org.seasar.doma.jdbc.dialect.Dialect
import org.seasar.doma.jdbc.dialect.H2Dialect
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource
import javax.sql.DataSource

/**
 * Created by drmashu on 2015/10/07.
 */
fun main(args: Array<String>){
    BuriRunner(Koshop()).start(8080)
}

class Koshop() : Buri() {
    init {

    }
    val datasource = LocalTransactionDataSource("jdbc:h2:koshop", "sa", "")
    val diarect = H2Dialect()
    override val config: Map<String, Factory<*>>
        get() = mapOf(
                "config" to Holder(object: Config {
                    override fun getDataSource(): DataSource? = datasource
                    override fun getDialect(): Dialect? = diarect
                }),
//                "itemDao" to Injection(ItemDaoImpl::class),
                "title" to Holder("Koshop"),
                "/" to Injection(pages.index::class),
                "/item/(?<id>[A-Za-z0-9]+)" to Injection(pages.item::class),
                "/search/(?<keyword>.*)" to Injection(SearchAction::class)
        )
}
