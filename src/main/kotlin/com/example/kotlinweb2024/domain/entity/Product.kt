package com.example.kotlinweb2024.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Product(
    @Id
    val id: String,
    var name: String,
    var quantity: Int=0
)
