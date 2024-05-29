package com.example.kotlinweb2024.api

import com.example.kotlinweb2024.common.DeityResponse
import com.example.kotlinweb2024.domain.codes.GoodsSalesCodes
import com.example.kotlinweb2024.domain.entity.Goods
import com.example.kotlinweb2024.service.GoodsSalesService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sales")
@Tag(name = "상품 판매", description = "상품 판매 API")
class SalesController(
    private val goodsSalesService:GoodsSalesService
) {

    @GetMapping("/display")
    fun displaySales(): ResponseEntity<DeityResponse<List<Goods>>> {

        return DeityResponse.success(goodsSalesService.displayGoods())

    }

    @PostMapping("/sell")
    fun sellGoods(goodsId:String, saleCode:GoodsSalesCodes, quantity:Int): ResponseEntity<DeityResponse<List<Goods>>> {
        return DeityResponse.success(goodsSalesService.sellGoods(goodsId, saleCode, quantity))
    }
}