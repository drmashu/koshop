package io.github.drmashu.koshop.model

import org.seasar.doma.Entity
import org.seasar.doma.Id

/**
 * Created by ‹M”Ž on 2015/09/26.
 */
public open class User()
public class Manager(): User()
public class Customer(var account: Account) : User() {
    val cart: MutableList<Items> = emptyList<Items>() as MutableList<Items>
}
@Entity
public data class Account(@Id val id: String, val name: String, val password: String, val postalCode: String, val address: String, val phone: String)
