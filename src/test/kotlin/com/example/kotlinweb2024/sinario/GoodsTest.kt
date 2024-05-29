package com.example.kotlinweb2024.sinario

import com.example.kotlinweb2024.domain.codes.GoodsSalesCodes
import com.example.kotlinweb2024.domain.entity.Goods
import com.example.kotlinweb2024.domain.entity.Product
import com.example.kotlinweb2024.domain.entity.dto.GoodsParts
import com.example.kotlinweb2024.repository.GoodsRepository
import com.example.kotlinweb2024.service.GoodsSalesService
import com.example.kotlinweb2024.service.GoodsService
import com.example.kotlinweb2024.service.ProductService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class GoodsTest(
    @Autowired val goodsRepository: GoodsRepository, //이건 단순히 엔티티를 확인하기 위해서만 사용한다.
    @Autowired val productService: ProductService,
    @Autowired val goodsService: GoodsService,
    @Autowired var goodsSaleService: GoodsSalesService,

    ): DescribeSpec(){



    init{
        describe("Goods"){
            context("상품 구성"){
                it("저장된 entity와 저장을 보낸 entity는 같다."){
                    val goodsId = "G1101"
                    val goodsName = "코카콜라"
                    val price = 10000

                    var makeGoods: Goods = Goods(goodsName, price)

                    makeGoods.id = goodsId
                    var saveGoods = goodsRepository.save(makeGoods)

                    makeGoods shouldBe saveGoods
                }

                it("상품 1은 제품1 10개과 제품2 1개로 구성해서 만든다."){
                    //given
                    val goodsId = "G1101"
                    val goodsName = "코카콜라"
                    val price = 10000
                    goodsService.makeGoods(goodsId, goodsName, price)
                    val (제품1, 제품2) = 상품BOM에넣을제품만들기()

                    var 상품구성요소들:List<GoodsParts> = listOf(
                        GoodsParts(제품1, 10),
                        GoodsParts(제품2, 1)
                    )

                    //when
                    goodsService.makeGoodsBom(goodsId,상품구성요소들)

                    //then
                    val goodsBomList = goodsService.goodsPartsList(goodsId)
                    goodsBomList.size shouldBe 2
                }

            }
            context("상품 판매"){
                it("상품 구매 요청, 제품구입정책 필요"){
                    //given 상품구매서를 작성한다.
                    val goodsId = "G1101"
                    코카콜라_1개구성(goodsId)

                    //when
                    goodsService.purchaseGoodsBom(goodsId)

                    //then
                }
                it("상품을 판매한다"){
                    //given
                    제품이있다()
                    val 제품리스트 = goodsSaleService.displayGoods()

                    //when
                    val randomIndex = (0 until 제품리스트.size).random()
                    val goods = 제품리스트[randomIndex]
                    val 상품번호 = goods.id
                    val 판매개수 = 1
                    val 판매방법 = GoodsSalesCodes.SALE

                    val 남은제품리스트 = goodsSaleService.sellGoods(상품번호,판매방법,판매개수)

                    //then
                    println("남은제품리스트 = ${남은제품리스트}")

                }
            }


        }
    }

    private fun 제품이있다() {
        val goodsId = "G1101"
        코카콜라_1개구성(goodsId)
    }

    private fun 코카콜라_1개구성(goodsId:String) {
        val goodsName = "코카콜라"
        val price = 10000
        var saveGoods = goodsService.makeGoods(goodsId, goodsName, price)
        val (제품1, 제품2) = 상품BOM에넣을제품만들기()

        var 상품구성요소들:List<GoodsParts> = listOf(
            GoodsParts(제품1, 10),
            GoodsParts(제품2, 1)
        )

        //when
        goodsService.makeGoodsBom(goodsId,상품구성요소들)
    }

    fun 상품BOM에넣을제품만들기(): Pair<Product, Product> {
        val product1Id = "B1104"
        val product1Name = "코카콜라"
        val product1Quantity = 10
        Product(product1Id, product1Name, product1Quantity)

        val product2Id = "B1105"
        val product2Name = "펩시콜라"
        val product2Quantity = 1
        Product(product2Id, product2Name, product2Quantity)

        productService.newMakeProduct(product1Id, product1Name, product1Quantity)
        productService.newMakeProduct(product2Id, product2Name, product2Quantity)

        val 제품1 = productService.searchProduct(product1Id)
        val 제품2 = productService.searchProduct(product2Id)
        return Pair(제품1, 제품2)
    }
}