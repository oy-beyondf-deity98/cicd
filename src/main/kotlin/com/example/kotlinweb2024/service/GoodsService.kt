package com.example.kotlinweb2024.service

import com.example.kotlinweb2024.domain.entity.Goods
import com.example.kotlinweb2024.domain.entity.GoodsBom
import com.example.kotlinweb2024.domain.entity.dto.GoodsParts

interface GoodsService {
    fun makeGoodsBom(goodsId: String, goodsPartsList:List<GoodsParts>):MutableList<GoodsBom>

    fun makeGoods(goodsId: String, goodsName: String, price: Int): Goods
    fun makeGoods(goodsId: String, goodsName: String, price: Int, stock:Int): Goods

    fun purchaseGoodsBom(goodsId: String)

    fun goodsPartsList(goodsId:String):List<GoodsBom>

}