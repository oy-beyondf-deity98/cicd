package com.example.kotlinweb2024.service

import com.example.kotlinweb2024.domain.codes.GoodsSalesCodes
import com.example.kotlinweb2024.domain.entity.Goods

interface GoodsSalesService {
    fun resistSoldGoods(soldGoods: Goods, soldGoodsQuantity: Int, saleCode: GoodsSalesCodes)
    fun displayGoods():List<Goods>
    fun subtractGoodsStock(soldGoods: Goods, soldGoodsQuantity:Int)
    fun sellGoods(goodsId: String, saleCode: GoodsSalesCodes, quantity: Int): List<Goods>
}