package io.github.drmashu.koshop

import io.github.drmashu.koshop.model.*

/**
 * Created by drmashu on 2015/10/07.
 */
fun main(args: Array<String>){
    val obj = Account(UID("id"), Name("name"), Password("password"), PostalCode("448-0813"), Address("address"), Phone("000"))
    val clazz = obj.javaClass
    clazz.fields.forEach {
        print("field ${it.name} ")
        it.annotations.forEach {
            print("${it.javaClass.name} ")
        }
        println()
    }
    clazz.methods.forEach {
        print("method ${it.name}")
        it.annotations.forEach {
            print("${it.javaClass.name} ")
        }
        println()
    }
}