package io.github.drmashu.koshop.dao

import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.Item
import io.github.drmashu.koshop.model.ItemId
import io.github.drmashu.koshop.model.ItemImage
import io.github.drmashu.koshop.model.ItemName
import org.seasar.doma.*

/**
 * Created by drmashu on 2015/10/18.
 */
@Dao
@InjectDomaConfig
public interface ItemImageDao {
    @Select fun selectById(id: ItemId, index: Int): List<ItemImage>
    @Select fun selectByItemId(id: ItemId): List<ItemImage>
    @Select fun selectByItemIdWithoutBlob(id: ItemId): List<ItemImage>
    @Insert fun Insert(itemImage: ItemImage): Int
    @Update fun update(itemImage: ItemImage): Int
    @Delete fun delete(itemImage: ItemImage): Int
}