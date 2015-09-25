/**
 * Created by ‹M”Ž on 2015/09/26.
 */
class User() {
    val cart: MutableList<Items> = emptyList<Items>() as MutableList<Items>
    var account: Account? = null
}
data class Account(val id: String, val name: String, val password: String, val postalCode: String, val address: String, val phone: String)
