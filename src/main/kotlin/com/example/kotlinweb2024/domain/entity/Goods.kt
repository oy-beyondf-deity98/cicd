package com.example.kotlinweb2024.domain.entity

import com.example.kotlinweb2024.domain.codes.GoodsCodes
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Goods(
    var name: String,
    var price: Int,
    var stock: Int = 0,
    var goodsCodes : GoodsCodes? = null
){
    @Id
    var id: String= ""
}

