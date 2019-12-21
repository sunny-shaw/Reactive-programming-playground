package com.engineer.reactiveprogramming.playground

import org.junit.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxTest {
    @Test
    fun flux1() {
        val flux = Flux.just("A", "B", "C")
                .log()

        StepVerifier.create(flux)
                .expectNext("A")
                .expectNext("B")
                .expectNext("C")
                .verifyComplete()
    }

    @Test
    fun flux2() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Error occurred")))
                .log()

        StepVerifier.create(flux)
                .expectNext("A", "B", "C")
                .expectErrorMessage("Error occurred")
                //.expectError(RuntimeException::class.java)
                .verify()
    }
}