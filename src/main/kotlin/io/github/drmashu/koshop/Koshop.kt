package io.github.drmashu.koshop

import io.github.drmashu.buri.Buri
import io.github.drmashu.buri.BuriRunner
import io.github.drmashu.dikon.*
import io.github.drmashu.koshop.action.ItemAction
import io.github.drmashu.koshop.action.ItemImgAction
import io.github.drmashu.koshop.action.ItemManageAction
import io.github.drmashu.koshop.action.SearchAction
import org.apache.logging.log4j.LogManager
import org.seasar.doma.jdbc.Config
import org.seasar.doma.jdbc.UtilLoggingJdbcLogger
import org.seasar.doma.jdbc.dialect.Dialect
import org.seasar.doma.jdbc.dialect.H2Dialect
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource
import org.seasar.doma.jdbc.tx.LocalTransactionManager
import org.seasar.doma.jdbc.tx.TransactionManager
import java.util.logging.Level
import javax.sql.DataSource

val mainLogger = LogManager.getLogger("KoshopMain")
/**
 * Created by drmashu on 2015/10/07.
 */
fun main(args: Array<String>){
    mainLogger.entry()
    BuriRunner(Koshop).start(8080)
    mainLogger.exit()
}

object Koshop : Buri() {
    val datasource = LocalTransactionDataSource("jdbc:h2:file:./db/koshop", "sa", "")
    val diarect = H2Dialect()
    val jdbcLogger = UtilLoggingJdbcLogger(Level.FINE)
    val transactionManager: LocalTransactionManager = LocalTransactionManager(
            datasource.getLocalTransaction(jdbcLogger))
    init {
        val tran = datasource.getLocalTransaction(jdbcLogger)
        tran.begin()
        val conn = datasource.connection
        conn.createStatement().execute("""CREATE TABLE IF NOT EXISTS account (
            id          NVARCHAR2(256)  NOT NULL PRIMARY KEY,
            name        NVARCHAR2(100) NOT NULL,
            password    NVARCHAR2(20),
            postal_code NVARCHAR2(10),
            address     NVARCHAR2(100),
            phone       NVARCHAR2(20)
        );""")
        conn.createStatement().execute("""CREATE TABLE IF NOT EXISTS item (
          id          NVARCHAR2(20)  NOT NULL PRIMARY KEY,
          name        NVARCHAR2(100) NOT NULL,
          price       INT            NOT NULL,
          description NVARCHAR2(1024)
        );
        """)
        conn.createStatement().execute("""CREATE TABLE IF NOT EXISTS items (
          uid     NVARCHAR2(256) NOT NULL,
          item_id NVARCHAR2(20)  NOT NULL,
          count   INT            NOT NULL,
          PRIMARY KEY (uid, item_id)
        );""")
        conn.createStatement().execute("""CREATE TABLE IF NOT EXISTS item_image (
          item_id NVARCHAR2(20) NOT NULL,
          `index`   TINYINT       NOT NULL,
          image   IMAGE,
          contentType VARCHAR2(256),
          PRIMARY KEY (item_id, `index`)
        );""")
        tran.commit()
        conn.close()
    }
    override val config: Map<String, Factory<*>>
        get() {
            return mapOf(
                    "doma_config" to Holder(object: Config {
                        override fun getDataSource(): DataSource? = datasource
                        override fun getDialect(): Dialect? = diarect
                        override fun getTransactionManager(): TransactionManager? = transactionManager
                    }),
                    "itemDao" to Injection(getKClassForName("io.github.drmashu.koshop.dao.ItemDaoImpl")),
                    "itemsDao" to Injection(getKClassForName("io.github.drmashu.koshop.dao.ItemsDaoImpl")),
                    "itemImageDao" to Injection(getKClassForName("io.github.drmashu.koshop.dao.ItemImageDaoImpl")),
                    "accountDao" to Injection(getKClassForName("io.github.drmashu.koshop.dao.AccountDaoImpl")),
                    "title" to Holder("Koshop"),
                    "/" to Injection(pages.index::class),
                    "/search/(?<keyword>.*)" to Injection(SearchAction::class),
                    "/manage/" to Injection(pages.index::class),
                    "/manage/item/(?<id>[A-Za-z0-9]+)" to Injection(ItemManageAction::class),
                    "/manage/item/" to Injection(pages.manage.index::class),
                    "/itemImg/(?<id>[A-Za-z0-9]+)/(?<index>[0-9]+)" to Injection(ItemImgAction::class),
                    "/item/(?<id>[A-Za-z0-9]+)" to Injection(ItemAction::class)
            )
        }
}
