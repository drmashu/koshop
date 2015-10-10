package io.github.drmashu.koshop.dao

import io.github.drmashu.koshop.model.Account
import io.github.drmashu.koshop.model.UID
import org.seasar.doma.*

/**
 * Created by drmashu on 2015/10/08.
 */
@Dao
interface AccountDao {
    @Select fun selectAll(): List<Account>
    @Select fun selectById(id: UID): Account
    @Insert fun Insert(account: Account): Int
    @Update fun update(account: Account): Int
    @Delete fun delete(account: Account): Int
    @Delete fun delete(id: UID): Int
}