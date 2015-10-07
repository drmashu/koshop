package io.github.drmashu.koshop.model

import org.seasar.doma.Domain
import org.seasar.doma.Entity
import org.seasar.doma.Id
/**
 * Created by NAGASAWA Takahiro on 2015/09/26.
 */
@Entity
public data class Item(@Id val id: ItemId, val name: ItemName, val price: Double, val description: Description)
@Entity
public data class Items(val item: Item, var count: Int)
@Domain(valueType = String::class)
public class ItemId(val value:String)
@Domain(valueType = String::class)
public class ItemName(val value:String)
@Domain(valueType = String::class)
public class Description(val value:String)
