package com.example.kotlinweb2024.domain.entity.dto

import com.example.kotlinweb2024.domain.entity.Product

data class GoodsParts(
    var product: Product,
    var quantity: Int
)