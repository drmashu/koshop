package io.github.drmashu.koshop.dao

import io.github.drmashu.koshop.doma.InjectDomaConfig
import io.github.drmashu.koshop.model.Item
import io.github.drmashu.koshop.model.ItemId
import io.github.drmashu.koshop.model.ItemName
import org.seasar.doma.*

/**
 * Created by 貴博 on 2015/09/29.
 */
@Dao
@InjectDomaConfig
public interface ItemDao {
    @Select fun selectAll(): List<Item>
    @Select fun selectById(id: ItemId): Item
    @Select fun selectByName(name: ItemName): List<Item>
    @Insert fun Insert(item: Item): Int
    @Update fun update(item: Item): Int
    @Delete fun delete(item: Item): Int
}