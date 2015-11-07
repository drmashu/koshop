package io.github.drmashu.koshop.dao

import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.Totp
import org.seasar.doma.*

/**
 * Created by drmashu on 2015/10/08.
 */
@Dao
@InjectDomaConfig
public interface TotpDao {
    @Select fun selectById(uid: Int): Totp
    @Insert fun insert(totp: Totp): Int
    @Update fun update(totp: Totp): Int
    @Delete fun delete(totp: Totp): Int
}
