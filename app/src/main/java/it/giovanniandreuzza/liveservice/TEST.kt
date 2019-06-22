package it.giovanniandreuzza.liveservice

import kotlin.reflect.KClass

enum class TEST(val clazz: KClass<*>) {

    A(Int::class), B(String::class)

}