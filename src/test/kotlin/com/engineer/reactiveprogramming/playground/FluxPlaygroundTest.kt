package com.engineer.reactiveprogramming.playground

import org.junit.Test
import reactor.core.publisher.Flux
import java.lang.RuntimeException
import java.time.Duration

class FluxPlaygroundTest {
    @Test
    fun fluxTest() {
        Flux.just("A", "B", "C")
                .log()
                .subscribe { data -> println(data) }
    }

    @Test
    fun fluxWithError() {
        Flux.just("A", "B", "C")
                //.concatWith(Flux.error(RuntimeException("Some exception occurred")))
                .log()
                .subscribe({ data -> println(data) },
                        { error -> println("Error is $error") },
                        { println("Completed") })
    }

    @Test
    fun fluxWithIterable() {
        Flux.fromIterable(listOf("A", "B", "C"))
                .log()
                .subscribe { data -> println(data) }
    }

    @Test
    fun fluxWithRange() {
        Flux.range(5, 6)
                //.log()
                .subscribe { data -> println(data) }
    }

    @Test
    fun fluxFromInterval() {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .subscribe { data -> println(data) }
        Thread.sleep(10000)
    }

    @Test
    fun fluxWithTakeOperator() {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .take(2)
                .subscribe { data -> println(data) }
        Thread.sleep(5000)
    }

    @Test
    fun fluxWithRequest() {
        Flux.range(1,9)
                .log()
                .subscribe ({ data -> println(data) },{}, {}, {sub -> sub.request(3)})
        Thread.sleep(5000)
    }

    @Test
    fun fluxWithErrorHandling() {
        Flux.just("A","B","C")
                .concatWith(Flux.error(RuntimeException("Some Exception")))
                .onErrorReturn("Some item on exception")
                .log()
                .subscribe { data -> println(data) }
        Thread.sleep(5000)
    }

}