package com.engineer.reactiveprogramming.playground.operatorTest

import org.junit.Test
import reactor.core.publisher.Flux

class MapTest {
    @Test
    fun mapTest() {
        Flux.range(1, 5)
                .map { "$it String form" }
                .subscribe { println(it) }
    }

    @Test
    fun mapTest2() {
        Flux.range(1, 10)
                .map { it * it }
                .filter { it % 2 == 0 }
                .subscribe { println(it) }
    }
}