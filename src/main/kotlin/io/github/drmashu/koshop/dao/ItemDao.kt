package io.github.drmashu.koshop.dao

import decode63
import encode63
import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.mainLogger
import io.github.drmashu.koshop.model.Item
import org.seasar.doma.*
import org.seasar.doma.jdbc.SelectOptions
import java.math.BigInteger
import kotlin.math.plus

/**
 * Created by 貴博 on 2015/09/29.
 */
@Dao
@InjectDomaConfig
public interface ItemDao {
    @Select fun selectAll(): List<Item>
    @Select fun selectById(id: String): Item
    @Select fun selectByName(name: String): List<Item>
    @Insert fun insert(item: Item): Int
    @Update fun update(item: Item): Int
    @Delete fun delete(item: Item): Int
    @Select fun selectMaxId(): String?
}
public fun ItemDao.getNextId():String {
    mainLogger.entry()
    val id = this.selectMaxId() ?: "0"
    val result = encode63(decode63(id) + BigInteger.ONE)
    mainLogger.exit(result)
    return result
}