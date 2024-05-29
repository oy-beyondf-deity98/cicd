package com.example.kotlinweb2024.goods

import com.example.kotlinweb2024.domain.codes.GoodsSalesCodes
import com.example.kotlinweb2024.domain.entity.Goods
import com.example.kotlinweb2024.domain.entity.GoodsSales
import com.example.kotlinweb2024.repository.GoodsRepository
import com.example.kotlinweb2024.repository.GoodsSaleRepository
import com.example.kotlinweb2024.service.GoodsSalesService
import org.springframework.stereotype.Service

@Service
class GoodsSalesServiceImpl(
    private val goodsSaleRepository: GoodsSaleRepository,
    private val goodsRepository: GoodsRepository,
) : GoodsSalesService {
    override fun resistSoldGoods(soldGoods: Goods, soldGoodsQuantity: Int, saleCode: GoodsSalesCodes) {
        goodsSaleRepository.save(GoodsSales(soldGoods, saleCode, soldGoodsQuantity, soldGoods.price))
    }

    override fun displayGoods(): List<Goods> {
        return goodsRepository.findAll()
    }

    override fun subtractGoodsStock(soldGoods: Goods, soldGoodsQuantity: Int) {
        soldGoods.stock -= soldGoodsQuantity

        goodsRepository.save(soldGoods)
    }

    override fun sellGoods(goodsId: String, saleCode: GoodsSalesCodes, quantity: Int): List<Goods> {
        var 판매상품 = goodsRepository.findById(goodsId).get()
        resistSoldGoods(판매상품,quantity,saleCode)

        subtractGoodsStock(판매상품,quantity)

        return goodsRepository.findAll()
    }
}