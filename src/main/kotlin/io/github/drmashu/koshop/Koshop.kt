package io.github.drmashu.koshop

import io.github.drmashu.buri.Buri
import io.github.drmashu.buri.BuriRunner
import io.github.drmashu.buri.TemplateHolder
import io.github.drmashu.dikon.*
import io.github.drmashu.koshop.action.ItemAction
import io.github.drmashu.koshop.action.ItemImgAction
import io.github.drmashu.koshop.action.SearchAction
import io.github.drmashu.koshop.action.manage.*
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
    BuriRunner(Koshop()).start(8080)
    mainLogger.exit()
}

public class Koshop() : Buri() {
    val datasource = LocalTransactionDataSource("jdbc:h2:file:./db/koshop;CIPHER=AES", "sa", "password password")
    val diarect = H2Dialect()
    val jdbcLogger = UtilLoggingJdbcLogger(Level.FINE)
    val transactionManager: LocalTransactionManager = LocalTransactionManager(
            datasource.getLocalTransaction(jdbcLogger))
    init {
        val tran = datasource.getLocalTransaction(jdbcLogger)
        tran.begin()
        val conn = datasource.connection
        conn.createStatement().execute("""
        CREATE TABLE IF NOT EXISTS account (
          id          INT NOT NULL PRIMARY KEY,
          mail        NVARCHAR2(256) NOT NULL,
          name        NVARCHAR2(100) NOT NULL,
          password    NVARCHAR2(20),
          role        INT
        );
        """)
        conn.createStatement().execute("""
        CREATE TABLE IF NOT EXISTS totp (
          id          INT NOT NULL PRIMARY KEY,
          key         NVARCHAR2(1024) NOT NULL
        );
        """)
        conn.createStatement().execute("""
        CREATE TABLE IF NOT EXISTS customer (
          id          INT  NOT NULL PRIMARY KEY,
          postal_code NVARCHAR2(10),
          address     NVARCHAR2(100),
          phone       NVARCHAR2(20)
        );
        """)
        conn.createStatement().execute("""
        CREATE TABLE IF NOT EXISTS item (
          id          NVARCHAR2(20)  NOT NULL PRIMARY KEY,
          name        NVARCHAR2(100) NOT NULL,
          price       INT            NOT NULL,
          description NVARCHAR2(1024)
        );
        """)
        conn.createStatement().execute("""
        CREATE TABLE IF NOT EXISTS items (
          uid     NVARCHAR2(256) NOT NULL,
          item_id NVARCHAR2(20)  NOT NULL,
          count   INT            NOT NULL,
          PRIMARY KEY (uid, item_id)
        );
        """)
        conn.createStatement().execute("""
        CREATE TABLE IF NOT EXISTS item_image (
          item_id NVARCHAR2(20) NOT NULL,
          `index`   TINYINT       NOT NULL,
          image   IMAGE,
          contentType VARCHAR2(256),
          PRIMARY KEY (item_id, `index`)
        );
        """)
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
                    "totpDao" to Injection(getKClassForName("io.github.drmashu.koshop.dao.TotpDaoImpl")),
                    "koshop_config" to Holder(KoshopConfig(
                            title = "KoshopSample",
                            description = """
                            Koshopのサンプルです。
                            """
                    )),
                    "authenticate_location" to Holder("/login"),
                    "/" to TemplateHolder("/index", arrayOf("koshop_config")),
                    "/search/(?<keyword>.*)" to Injection(SearchAction::class),
                    "/manage/" to Injection(ManageLoginAction::class),
                    "/manage/login" to Injection(ManageLoginAction::class),
                    "/manage/accounts" to Injection(ManageAccountListAction::class),
                    "/manage/account/(?<id>[0-9]+)" to Injection(ManageAccountAction::class),
                    "/manage/items" to Injection(ManageItemListAction::class),
                    "/manage/item/(?<id>[A-Za-z0-9]{20})" to Injection(ManageItemAction::class),
                    "/itemImg/(?<id>[A-Za-z0-9]{20})/(?<index>[0-9]+)" to Injection(ItemImgAction::class),
                    "/items" to Injection(ItemListAction::class),
                    "/item/(?<id>[A-Za-z0-9]{20})" to Injection(ItemAction::class)
            )
        }
}
class KoshopConfig(val title: String, val description: String)
