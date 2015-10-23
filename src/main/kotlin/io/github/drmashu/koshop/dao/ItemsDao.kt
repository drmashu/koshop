package io.github.drmashu.koshop.dao

import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.Items
import org.seasar.doma.*

/**
 * Created by drmashu on 2015/10/18.
 */
@Dao
@InjectDomaConfig
public interface ItemsDao {
    @Select fun selectById(uid: String, itemId: String): List<Items>
    @Select fun selectByUid(uid: String): List<Items>
    @Insert fun insert(items: Items): Int
    @Update fun update(items: Items): Int
    @Delete fun delete(items: Items): Int
}