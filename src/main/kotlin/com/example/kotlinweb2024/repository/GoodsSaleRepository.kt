package com.example.kotlinweb2024.repository

import com.example.kotlinweb2024.domain.entity.GoodsSales
import org.springframework.data.jpa.repository.JpaRepository

interface GoodsSaleRepository: JpaRepository<GoodsSales, Int>