package com.example.kotlinweb2024.domain.entity.codes

enum class ProductPurchasePolicy(val productName: String, val quantity: Int, val price: Double) {
    B1104("코카콜라", 10, 1000.0),
    B1105("펩시콜라", 10, 1000.0);

    companion object {
        fun convertFromCodes(productId: String): ProductPurchasePolicy =
            values().first { it.name == productId }
    }
}