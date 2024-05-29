package com.example.kotlinweb2024.domain.entity

import jakarta.persistence.*

@Entity
data class ProductBom(
    @Id
    @GeneratedValue
    var seq: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    var product: Product,
    var quantity: Int
)
