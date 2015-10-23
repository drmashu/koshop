package io.github.drmashu.koshop.dao

import decode63
import encode63
import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.Account
import io.github.drmashu.koshop.model.Role
import org.seasar.doma.*
import org.seasar.doma.jdbc.Config
import org.seasar.doma.jdbc.builder.SelectBuilder
import java.math.BigInteger
import kotlin.math.plus

/**
 * Created by drmashu on 2015/10/08.
 */
@Dao
@InjectDomaConfig
public interface AccountDao {
    @Select fun selectAll(): List<Account>
    @Select fun selectById(uid: Int): Account
    @Insert fun insert(account: Account): Int
    @Update fun update(account: Account): Int
    @Delete fun delete(account: Account): Int
    @Select fun selectMaxId(): Int
    @Select fun countByRole(role: Role): Int
}

public fun AccountDao.getNextId():Int {
    val id = this.selectMaxId()
    return 1 + id
}