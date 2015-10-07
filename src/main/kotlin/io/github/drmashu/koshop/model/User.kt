package io.github.drmashu.koshop.model

import org.seasar.doma.Domain
import org.seasar.doma.Entity
import org.seasar.doma.Id

/**
 * Created by 貴博 on 2015/09/26.
 */
public open class User()
public class Manager(): User()
public class Customer(var account: Account) : User() {
    val cart: MutableList<Items> = emptyList<Items>() as MutableList<Items>
}
@Entity
public class Account(@Id var id: UID, var name: Name, var password: Password, var postalCode: PostalCode, var address: Address, var phone: Phone)
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
