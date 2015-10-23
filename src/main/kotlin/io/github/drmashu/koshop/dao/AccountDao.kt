package io.github.drmashu.koshop.dao

import decode63
import encode63
import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.Account
import org.seasar.doma.*
import java.math.BigInteger
import kotlin.math.plus

/**
 * Created by drmashu on 2015/10/08.
 */
@Dao
@InjectDomaConfig
public interface AccountDao {
    @Select fun selectAll(): List<Account>
    @Select fun selectById(uid: String): Account
    @Insert fun insert(account: Account): Int
    @Update fun update(account: Account): Int
    @Delete fun delete(account: Account): Int
    @Select fun selectMaxId(): String
}
public fun AccountDao.getNextId():String {
    val id = this.selectMaxId()
    return encode63(decode63(id) + BigInteger.ONE)
}