package io.github.drmashu.koshop

import io.github.drmashu.buri.Buri
import io.github.drmashu.buri.BuriRunner
import io.github.drmashu.dikon.Factory
import io.github.drmashu.dikon.Holder
import io.github.drmashu.dikon.Injection

/**
 * Created by drmashu on 2015/10/07.
 */
fun main(args: Array<String>){
    BuriRunner(Koshop()).start(8080)
}

class Koshop : Buri() {
    override val config: Map<String, Factory<*>>
        get() = mapOf(
                "title" to Holder("Koshop"),
                "/" to Injection(pages.index::class),
                "/item" to Injection(pages.item::class)
        )
}
