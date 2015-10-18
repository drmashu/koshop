package io.github.drmashu.koshop.dao

import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.ItemId
import io.github.drmashu.koshop.model.ItemImage
import io.github.drmashu.koshop.model.Items
import io.github.drmashu.koshop.model.UID
import org.seasar.doma.*

/**
 * Created by drmashu on 2015/10/18.
 */
@Dao
@InjectDomaConfig
public interface ItemsDao {
    @Select fun selectById(uid: UID, itemId: ItemId): List<Items>
    @Select fun selectByUid(uid: UID): List<Items>
    @Insert fun Insert(items: Items): Int
    @Update fun update(items: Items): Int
    @Delete fun delete(items: Items): Int
}