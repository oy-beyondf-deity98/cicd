package com.example.kotlinweb2024.goods

import com.example.kotlinweb2024.domain.entity.Goods
import com.example.kotlinweb2024.domain.entity.GoodsBom
import com.example.kotlinweb2024.domain.entity.ProductPurchase
import com.example.kotlinweb2024.domain.entity.codes.ProductPurchasePolicy
import com.example.kotlinweb2024.domain.entity.dto.GoodsParts
import com.example.kotlinweb2024.repository.GoodsBomRepository
import com.example.kotlinweb2024.repository.GoodsRepository
import com.example.kotlinweb2024.repository.ProductPurchaseRepository
import com.example.kotlinweb2024.repository.ProductRepository
import com.example.kotlinweb2024.service.GoodsService
import org.springframework.stereotype.Service

@Service
class GoodsServiceImpl(
    private val goodsBomRepository: GoodsBomRepository,
    private val goodsRepository: GoodsRepository,
    private val productRepository: ProductRepository,
    private val productPurchaseRepository: ProductPurchaseRepository,
) : GoodsService {
    override fun makeGoodsBom(goodsId: String, goodsPartsList: List<GoodsParts>): MutableList<GoodsBom> {

        checkProductExits(goodsPartsList)

        goodsPartsList.forEach {
            var saveGoodsBom = GoodsBom(it.quantity)
            saveGoodsBom.goodsId = goodsId
            saveGoodsBom.productId = it.product.id
            goodsBomRepository.save(saveGoodsBom)
        }

        return goodsBomRepository.findByGoodsId(goodsId).toMutableList()
    }

    private fun checkProductExits(goodsPartsList: List<GoodsParts>) {
        val findProduct = productRepository.findAll()
        if (findProduct.isEmpty()) {
            throw IllegalArgumentException("제품이 존재하지 않습니다.")
        }


        val displayProductIdList = findProduct.map { it.id }
        val goodsPartsProductIdList = goodsPartsList.map { it.product.id }

        if (!displayProductIdList.containsAll(goodsPartsProductIdList)) {
            throw IllegalArgumentException("일부 제품이 존재하지 않습니다.")
        }
    }

    override fun makeGoods(goodsId: String, goodsName: String, price: Int): Goods {
        var makeGoods = Goods(goodsName, price)

        makeGoods.id = goodsId
        goodsRepository.save(makeGoods)
        return makeGoods
    }

    override fun goodsPartsList(goodsId: String):List<GoodsBom> {
        return goodsBomRepository.findByGoodsId(goodsId)
    }

    override fun makeGoods(goodsId: String, goodsName: String, price: Int, stock: Int): Goods {
        TODO("Not yet implemented")
    }


    /**
     * 현재는 구매를 요청하면 바로 구매를 진행, 나중에는 구매요청서를 작성해서 승인이 나면 구매를 진행
     */
    override fun purchaseGoodsBom(goodsId: String) {
        val goodsBomList = goodsBomRepository.findByGoodsId(goodsId)

        goodsBomList.forEach {
            val product = productRepository.findById(it.productId).get()
            val purchasePolicy = ProductPurchasePolicy.convertFromCodes(it.productId)

            productPurchaseRepository.save(ProductPurchase(product, purchasePolicy.quantity, purchasePolicy.price))
        }
    }
}