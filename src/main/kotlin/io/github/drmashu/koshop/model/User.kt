package io.github.drmashu.koshop.model

import org.seasar.doma.Entity
import org.seasar.doma.Id
import org.seasar.doma.jdbc.entity.NamingType

/**
 * Created by 貴博 on 2015/09/26.
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Account() {
    @Id public var id: Int? = null
    public var mail: String? = null
    public var name: String? = null
    public var password: String? = null
    public var role: Role? = null
    public var gauth: Boolean? = null
}

@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Totp() {
    @Id public var id: Int? = null
    public var key: String? = null

}
interface UserBase {
    @Id public var id: Int?
    public var postalCode: String?
    public var address: String?
    public var phone: String?
}
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Customer(): UserBase {
    @Id public override var id: Int? = null
    public override var postalCode: String? = null
    public override var address: String? = null
    public override var phone: String? = null
}

public class User(customer:Customer): UserBase by customer {

    public val cart: MutableList<Items>? = null
}