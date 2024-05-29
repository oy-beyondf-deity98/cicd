package com.example.kotlinweb2024.domain.entity.dto

import com.example.kotlinweb2024.domain.entity.Product

data class PurchaseRequest(
    val product: Product
){
    var quantity: Int? = 0
    var price:Double = 0.0
}
