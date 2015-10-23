package io.github.drmashu.koshop.dao

import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.ItemImage
import org.seasar.doma.*

/**
 * Created by drmashu on 2015/10/18.
 */
@Dao
@InjectDomaConfig
public interface ItemImageDao {
    @Select fun selectById(id: String, index: Int): List<ItemImage>
    @Select fun selectByItemId(id: String): List<ItemImage>
    @Select fun selectByItemIdWithoutBlob(id: String): List<ItemImage>
    @Insert fun insert(itemImage: ItemImage): Int
    @Update fun update(itemImage: ItemImage): Int
    @Delete fun delete(itemImage: ItemImage): Int
}