package com.example.kotlinweb2024.repository

import com.example.kotlinweb2024.domain.entity.Goods
import org.springframework.data.jpa.repository.JpaRepository

interface GoodsRepository:JpaRepository<Goods,String> {

}
