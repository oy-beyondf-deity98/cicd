package com.example.kotlinweb2024.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class ProductPurchase(
    @ManyToOne
    val product: Product,

    val quantity: Int,
    val price: Double
){
    @Id
    @GeneratedValue
    val seq: Long = 0
}
