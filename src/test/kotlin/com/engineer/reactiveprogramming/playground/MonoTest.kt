package com.engineer.reactiveprogramming.playground

import org.junit.Test
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.lang.Exception

class MonoTest {
    @Test
    fun mono1() {
        Mono.just("A")
                .log()
                .subscribe { data -> println("My data is  $data")}
    }

    @Test
    fun mono2() {
        Mono.error<Exception>(Exception("Some exception"))
                .log()
                .doOnError{error -> println("Error occurred with message ${error.message}")}
                .subscribe()
    }

    @Test
    fun junitTest1() {
        val mono = Mono.just("A").log()

        StepVerifier.create(mono)
                .expectNext("A")
                .verifyComplete()
    }

    @Test
    fun junitTest2() {
        val mono = Mono.error<Exception>(Exception("Some exception")).log()

        StepVerifier.create(mono)
                .expectError(Exception::class.java)
                .verify()
    }
}
