package com.example.kotlinweb2024.domain.entity

import com.example.kotlinweb2024.domain.entity.keys.GoodsBomId
import jakarta.persistence.*
import org.apache.logging.log4j.util.Strings

@Entity
@IdClass(GoodsBomId::class)
data class GoodsBom(
    var quantity:Int,
){
    @Id
    var goodsId: String = Strings.EMPTY

    @Id
    var productId:String = Strings.EMPTY
}