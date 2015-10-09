package io.github.drmashu.koshop

import io.github.drmashu.buri.Buri
import io.github.drmashu.buri.BuriRunner
import io.github.drmashu.dikon.Factory
import io.github.drmashu.dikon.Injection
import io.github.drmashu.koshop.model.*

/**
 * Created by drmashu on 2015/10/07.
 */
fun main(args: Array<String>){
    BuriRunner(Koshop()).start(8080)
}

class Koshop : Buri() {
    override val config: Map<String, Factory<*>>
        get() = mapOf(
                "/" to Injection(pages.index::class)
        )

}
