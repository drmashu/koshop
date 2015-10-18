package io.github.drmashu.koshop.model

import org.seasar.doma.Domain
import org.seasar.doma.Entity
import org.seasar.doma.Id
import org.seasar.doma.jdbc.entity.NamingType

/**
 * Created by 貴博 on 2015/09/26.
 */
public open class User()
public class Manager(): User()
public class Customer(var account: Account) : User() {
    val cart: MutableList<Items>? = null
}
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Account {
    @Id public var id: UID? = null
    public var name: Name? = null
    public var password: Password? = null
    public var postalCode: PostalCode? = null
    public var address: Address? = null
    public var phone: Phone? = null
}
@Domain(valueType = String::class)
public class UID(val value:String)
@Domain(valueType = String::class)
public class Name(val value:String)
@Domain(valueType = String::class)
public class Password(val value:String)
@Domain(valueType = String::class)
public class PostalCode(val value:String)
@Domain(valueType = String::class)
public class Address(val value:String)
@Domain(valueType = String::class)
public class Phone(val value:String)
