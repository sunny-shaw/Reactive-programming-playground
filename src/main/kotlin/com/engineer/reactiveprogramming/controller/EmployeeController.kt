package com.engineer.reactiveprogramming.controller

import com.engineer.reactiveprogramming.model.Employee
import com.engineer.reactiveprogramming.repository.EmployeesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("v1/employees")
class EmployeeController(
        @Autowired
        val employeesRepository: EmployeesRepository
) {

    @GetMapping
    fun getAllEmployees(): Flux<Employee> {
        return employeesRepository.findAll()
    }

    @GetMapping("{id}")
    fun getEmpoyeeById(@PathVariable id: String): Mono<Employee> {
        return employeesRepository.findById(id)
    }

    @PostMapping
    fun save(@RequestBody employee: Employee): Mono<Employee> {
        return employeesRepository.save(employee)
    }

    @PutMapping("/update")
    fun update(@RequestBody employee: Employee): Mono<Employee> {
        return employeesRepository.save(employee)
    }

    @DeleteMapping
    fun delete(): Mono<Void> {
        return employeesRepository.deleteAll()
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String): Mono<Void> {
        return employeesRepository.deleteById(id)
    }
}