package io.github.drmashu.koshop.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.seasar.doma.Domain
import org.seasar.doma.Entity
import org.seasar.doma.Transient
import org.seasar.doma.Id
import org.seasar.doma.jdbc.entity.NamingType
import java.math.BigDecimal
import java.sql.Blob

/**
 * Created by NAGASAWA Takahiro on 2015/09/26.
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Item {
    @Id public var id: ItemId? = null
    public var name: ItemName? = null
    public var price: BigDecimal? = null
    public var description: Description? = null
    @Transient
    public var images: List<ItemImage>? = null
}

/**
 *
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Items {
    @Id public var uid: UID? = null
    @Id public var itemId: ItemId? = null
    public var count: Int? = null
    @Transient
    public var item: Item? = null
}

/**
 *
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class ItemImage {
    @Id public var itemId: ItemId? = null
    @Id public var index: Byte? = null
    @JsonIgnore
    public var image: Blob? = null
}
@Domain(valueType = String::class)
public class ItemId(val value:String)
@Domain(valueType = String::class)
public class ItemName(val value:String)
@Domain(valueType = String::class)
public class Description(val value:String)
