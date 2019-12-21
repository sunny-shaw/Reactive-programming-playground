package com.engineer.reactiveprogramming.playground

import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class MockkDemoTest {
    @Test
    fun test() {

        //WORKS WITH mockk 1.9 and above
        val mockPerson = mockk<EmployeeRepository> {
            every { employee } returns mockk {
                every { age } returns 20
                every { name } returns "Some name"
            }
        }

//        val mockPerson = mockk<EmployeeRepository> {
//            every { employee.name } returns  "Some name"
//            every { employee.age } returns 20
//
//        }

        every { mockPerson.getName() } returns mockPerson.employee


//        val employee = mockk<Employee>()
//        val mockPerson = mockk<EmployeeRepository>()
//
//        every { employee.age } returns 20
//        every { employee.name } returns "Some name"
//        every { mockPerson.employee } returns employee
//        every { mockPerson.getName() } returns employee


        println ("person ${mockPerson.employee}")
        println ("mockPerson.getName() ${mockPerson.getName()}")

        println ("age ${mockPerson.employee.age}")
        println ("name ${mockPerson.employee.name}")
    }
}


data class EmployeeRepository(val employee: Employee){
    fun getName(): Employee{
        return Employee(30,"getName name")
    }
}

data class Employee(val age:Int, val name: String)