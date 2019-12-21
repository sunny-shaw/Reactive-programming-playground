package com.engineer.reactiveprogramming.controller

import org.assertj.core.api.Assertions
import org.junit.Test
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

data class Employee(val name: String)

class EmployeeRepository{
    val empList:List<Employee> = listOf(
            Employee("John"),
            Employee("Dan"),
            Employee("Abram")
    )

    fun getEmployees(): Flux<Employee> {
        return Flux.fromIterable(empList)
    }
}

class EmployeeTest{
    @Test
    fun test() {
        val employees:Flux<Employee> = EmployeeRepository().getEmployees()
        val data : Mono<ServerResponse> = ServerResponse.ok().body(employees, Employee::class.java)

        StepVerifier.create(data)
                .consumeNextWith { it: ServerResponse? -> println(it)
                }.verifyComplete()
    }
}