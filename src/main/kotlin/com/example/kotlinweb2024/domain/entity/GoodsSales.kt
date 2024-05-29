package com.example.kotlinweb2024.domain.entity

import com.example.kotlinweb2024.domain.codes.GoodsSalesCodes
import jakarta.persistence.*

@Entity
data class GoodsSales(
    @ManyToOne(fetch = FetchType.LAZY)
    val goods: Goods,
    val goodsSalesType: GoodsSalesCodes,
    val quantity: Int,
    val price: Int
){
    @Id
    @GeneratedValue
    val seq: Long = 0
}
