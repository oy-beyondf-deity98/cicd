package com.example.kotlinweb2024.repository

import com.example.kotlinweb2024.domain.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, String> {
}
