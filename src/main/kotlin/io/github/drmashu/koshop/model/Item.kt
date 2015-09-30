package io.github.drmashu.koshop.model

import org.seasar.doma.Entity
import org.seasar.doma.Id
/**
 * Created by ‹M”Ž on 2015/09/26.
 */
@Entity
public data class Item(@Id val id: String, val name: String, val price: Double, val description: String)
@Entity
public data class Items(val item: Item, var count: Int)
