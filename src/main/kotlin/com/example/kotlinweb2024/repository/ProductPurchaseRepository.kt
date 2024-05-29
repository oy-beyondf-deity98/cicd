package com.example.kotlinweb2024.repository

import com.example.kotlinweb2024.domain.entity.ProductPurchase
import org.springframework.data.jpa.repository.JpaRepository

interface ProductPurchaseRepository:JpaRepository<ProductPurchase,Long> {

}
