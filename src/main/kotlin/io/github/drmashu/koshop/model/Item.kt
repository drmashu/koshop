package io.github.drmashu.koshop.model

import org.seasar.doma.Domain
import org.seasar.doma.Entity
import org.seasar.doma.Id
/**
 * Created by NAGASAWA Takahiro on 2015/09/26.
 */
@Entity
public class Item {
    @Id public var id: ItemId? = null
    public var name: ItemName? = null
    public var price: Double? = null
    public var description: Description? = null
}
@Entity
public class Items {
    @Id public var uid: UID? = null
    @Id public var id: ItemId? = null
    public var count: Int? = null
    @Transient public val Item: Item?
        get() {
             return null
        }
}
@Domain(valueType = String::class)
public class ItemId(val value:String)
@Domain(valueType = String::class)
public class ItemName(val value:String)
@Domain(valueType = String::class)
public class Description(val value:String)
