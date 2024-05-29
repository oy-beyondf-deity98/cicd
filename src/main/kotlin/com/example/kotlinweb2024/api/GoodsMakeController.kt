package com.example.kotlinweb2024.api

import com.example.kotlinweb2024.common.DeityResponse
import com.example.kotlinweb2024.domain.entity.GoodsBom
import com.example.kotlinweb2024.domain.entity.dto.GoodsParts
import com.example.kotlinweb2024.service.GoodsService
import com.example.kotlinweb2024.service.ProductService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/goods/make")
@Tag(name = "상품 제작", description = "상품 제작 API")
class GoodsMakeController(
    private val goodsService: GoodsService,
    private val productService:ProductService
) {

    @PostMapping("/bom")
    fun makeGoodsBom(goodsId:String, goodsPartsList:List<GoodsPartsRequest>): ResponseEntity<DeityResponse<List<GoodsBom>>> {

        val goodsParts: MutableList<GoodsParts> = mutableListOf();

        goodsPartsList.forEach {
            var product = productService.searchProduct(it.productId)
            goodsParts += GoodsParts(product, it.quantity)
        }

        return DeityResponse.success(goodsService.makeGoodsBom(goodsId, goodsParts))
    }

    @GetMapping("/parts")
    fun makeParts(goodsId:String): ResponseEntity<DeityResponse<List<GoodsBom>>> {
        return DeityResponse.success(goodsService.goodsPartsList(goodsId))
    }

    @GetMapping("/purchase")
    fun purchaseGoods(goodsId:String): ResponseEntity<DeityResponse<String>> {
        goodsService.purchaseGoodsBom(goodsId)
        return DeityResponse.success()
    }

    @PostMapping("/")
    fun makeGoods(goodsId:String, goodsName:String, price:Int): ResponseEntity<DeityResponse<String>> {
        goodsService.makeGoods(goodsId, goodsName, price)
        return DeityResponse.success()
    }

    data class GoodsPartsRequest(
        val productId:String,
        val quantity:Int
    )





}