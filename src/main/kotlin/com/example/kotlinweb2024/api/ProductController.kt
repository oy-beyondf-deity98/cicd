package com.example.kotlinweb2024.api

import com.example.kotlinweb2024.common.DeityResponse
import com.example.kotlinweb2024.domain.entity.Product
import com.example.kotlinweb2024.service.ProductService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/product")
@RestController
@Tag(name = "제품 관리", description = "제품 제작 API")
class ProductController(
    private val productService: ProductService
) {
    @PutMapping("/add")
    fun addProduct(id:String, quantity:Int): ResponseEntity<DeityResponse<String>> {
        productService.addProduct(id,quantity)
        return DeityResponse.success()
    }

    @PostMapping("/")
    fun newMakeProduct(id:String, name:String, quantity:Int): ResponseEntity<DeityResponse<String>> {
        productService.newMakeProduct(id,name,quantity)
        return DeityResponse.success()
    }

    @DeleteMapping("/remove")
    fun removeProduct(id:String): ResponseEntity<DeityResponse<String>> {
        productService.removeProduct(id)
        return DeityResponse.success()
    }

    @GetMapping("/search")
    fun searchProduct(id:String): ResponseEntity<DeityResponse<Product>> {
        return DeityResponse.success(productService.searchProduct(id))
    }

    @GetMapping("/display")
    fun displayProducts(): ResponseEntity<DeityResponse<List<Product>>> {
        return DeityResponse.success(productService.displayProducts())
    }

}