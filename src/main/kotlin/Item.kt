/**
 * Created by ‹M”Ž on 2015/09/26.
 */
data class Item(val id: String, val name: String, val price: Double, val description: String)
data class Items(val item: Item, var count: Int)
