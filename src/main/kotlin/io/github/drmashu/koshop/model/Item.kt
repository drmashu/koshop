package io.github.drmashu.koshop.model

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @Id public var id: String? = null
    public var name: String? = null
    public var price: BigDecimal? = null
    public var description: String? = null
    @Transient
    public var images: MutableList<ItemImage>? = null
}

/**
 *
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Items {
    @Id public var uid: String? = null
    @Id public var itemId: String? = null
    public var count: Int? = null
    @Transient
    public var item: Item? = null
}

/**
 *
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class ItemImage {
    @Id public var itemId: String? = null
    @Id public var index: Byte? = null
    @JsonIgnore
    public var image: Blob? = null
    public var contentType: String? = null
}
