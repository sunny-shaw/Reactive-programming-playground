package com.engineer.reactiveprogramming.playground.operatorTest

import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.SignalType
import reactor.test.StepVerifier
import java.lang.RuntimeException
import java.time.Duration

class ErrorHandlingTest {
    @Test
    fun errorHandlingTest() {
        val flux = Flux.just("A", "B", "C")
                //.concatWith(Flux.error(RuntimeException("Some error")))
                .concatWith(Flux.just("D"))

        StepVerifier.create(flux.log())
                .expectNext("A", "B", "C", "D")
                //.expectError()
                .verifyComplete()
    }

    @Test
    fun doOnErrorTest() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Some error")))
                .doOnError{
                    error -> println("Some error occurred $error")
                }

        StepVerifier.create(flux.log())
                .expectNext("A", "B", "C")
                .expectError()
                .verify()
    }

    @Test
    fun onErrorReturn() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Some error")))
                .onErrorReturn("getName default value")

        StepVerifier.create(flux.log())
                .expectNext("A", "B", "C")
                .expectNext("getName default value")
                .verifyComplete()
    }

    @Test
    fun onErrorResume() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Some error")))
                .onErrorResume {
                    println("Some error occurred ${it.message}")

                    Flux.just("D")
                }

        StepVerifier.create(flux.log())
                .expectNext("A", "B", "C")
                .expectNext("D")
                .verifyComplete()
    }

    @Test
    fun onErrorMap() {
        val flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Business exception")))
                .onErrorMap {
                    when{
                        it.message == "Business exception" -> CustomException("Translated Exception")
                        else -> it
                    }
                }

        StepVerifier.create(flux.log())
                .expectNext("A", "B", "C")
                .expectErrorMessage("Translated Exception")
                //.expectError()
                .verify()
    }


    //    SignalType.ON_COMPLETE
//    SignalType.ON_ERROR
//    SignalType.CANCEL
    @Test
    fun doFinallyTest() {
        val flux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1))
                .doFinally {
                    when{
                        it == SignalType.CANCEL -> println("Perform operations on cancel")
                        it == SignalType.ON_COMPLETE -> println("Perform operations on complete")
                        it == SignalType.ON_ERROR -> println("Perform operations on error")
                    }
                }
                .log()
                .take(2)

        StepVerifier.create(flux)
                .expectNext("A", "B")
                .thenCancel()
                .verify()
    }
}

class CustomException(errorMesseage: String) : Throwable(errorMesseage)
