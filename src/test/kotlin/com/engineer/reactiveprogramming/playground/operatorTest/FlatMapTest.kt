package com.engineer.reactiveprogramming.playground.operatorTest

import org.junit.Test
import org.springframework.boot.context.properties.bind.Bindable.listOf
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import reactor.core.scheduler.Schedulers.parallel
import reactor.test.StepVerifier

class FlatMapTest {
    @Test
    fun flatMapTest() {
        val employeeIds = listOf("1", "2", "3", "4", "5", "6", "7", "8") //List<String>

        val employeeName = Flux.fromIterable(employeeIds) //Flux<String>
                .flatMap { id -> getEmployeeDetails(id) } //DB or external service call that return a flux or Mono// Mono<String>
                .log()

        StepVerifier.create(employeeName)
                .expectNextCount(8)
                .verifyComplete()
    }

    @Test
    fun flatMapTestUsingParallelScheduler() {
        val employeeIds = listOf("1", "2", "3", "4", "5", "6", "7", "8") //List<String>

        val employeeName = Flux.fromIterable(employeeIds) //Flux<String>
                .window(2) //Flux<Flux<String>>
                .flatMapSequential {
                    identifiers -> identifiers.flatMap {
                    id -> getEmployeeDetails(id)
                }.subscribeOn(parallel()) }
                .log()

        StepVerifier.create(employeeName)
                .expectNextCount(8)
                .verifyComplete()
    }

    //Mock DB or external service
    private fun getEmployeeDetails(id: String?): Mono<String> {
        val employees = mapOf(
                "1" to "Sam",
                "2" to "Ajay",
                "3" to "Ram",
                "4" to "Sameer",
                "5" to "Jay",
                "6" to "Ramesh",
                "7" to "Sami",
                "8" to "Arun"
        )

        Thread.sleep(1000)
        return employees.getOrDefault(id, "Not found").toMono()
    }
}