package com.engineer.reactiveprogramming.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document("employees")
data class Employee(
        @Id
        val id: String?,
        val name: String,
        val dept: String
)