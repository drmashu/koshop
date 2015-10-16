package io.github.drmashu.koshop.dao

import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.Account
import io.github.drmashu.koshop.model.UID
import org.seasar.doma.*

/**
 * Created by drmashu on 2015/10/08.
 */
@Dao
@InjectDomaConfig
interface AccountDao {
    @Select fun selectAll(): List<Account>
    @Select fun selectById(uid: UID): Account
    @Insert fun insert(account: Account): Int
    @Update fun update(account: Account): Int
    @Delete fun delete(account: Account): Int
}