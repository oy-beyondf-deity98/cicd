package com.example.kotlinweb2024.repository

import com.example.kotlinweb2024.domain.entity.GoodsBom
import com.example.kotlinweb2024.domain.entity.keys.GoodsBomId
import org.springframework.data.jpa.repository.JpaRepository

interface GoodsBomRepository:JpaRepository<GoodsBom,GoodsBomId> {
    fun findByGoodsId(goodsId: String): List<GoodsBom>

}
