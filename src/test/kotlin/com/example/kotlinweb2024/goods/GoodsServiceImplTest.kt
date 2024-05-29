package com.example.kotlinweb2024.goods

import com.example.kotlinweb2024.domain.entity.GoodsBom
import com.example.kotlinweb2024.domain.entity.Product
import com.example.kotlinweb2024.domain.entity.dto.GoodsParts
import com.example.kotlinweb2024.product.ProductServiceImpl
import com.example.kotlinweb2024.repository.GoodsBomRepository
import com.example.kotlinweb2024.repository.GoodsRepository
import com.example.kotlinweb2024.repository.ProductPurchaseRepository
import com.example.kotlinweb2024.repository.ProductRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class GoodsServiceImplTest: BehaviorSpec({
  val goodsRepository = mockk<GoodsRepository>()
  val goodsBomRepository = mockk<GoodsBomRepository>()
  val productRepository = mockk<ProductRepository>()
  val productPurchaseRepository = mockk<ProductPurchaseRepository>()

  val goodsService = GoodsServiceImpl(goodsBomRepository, goodsRepository, productRepository, productPurchaseRepository)
  val productService = ProductServiceImpl(productRepository)


    Given("상품구성을 구성할때하는"){
        val 상품번호 = "G1"
        val 구성제품아이디1 = "P1"
        val 구성제품아이디2 = "P2"
        val 구성제품1수량1 = 10
        val 구성제품2수량2 = 20

        When("제품 자체가 없으면") {
            every { productRepository.findAll() } returns listOf()

            Then("오류가 발생한다") {
                val exception = assertThrows(IllegalArgumentException::class.java) {
                    goodsService.makeGoodsBom(상품번호, listOf(GoodsParts(Product(구성제품아이디1, "커피", 10), 구성제품1수량1), GoodsParts(Product(구성제품아이디2, "커피", 10), 구성제품2수량2)))
                }
                assertEquals("제품이 존재하지 않습니다.", exception.message)
            }

            Then("서비스에 호출하는 메서드 확인") {
                verify(exactly = 1) { productRepository.findAll() }
            }
        }

        When("구성제품이 없으면") {
            every { productRepository.findAll() } returns listOf(Product(구성제품아이디1, "커피", 10))

            Then("오류가 발생한다") {
                val exception = assertThrows(IllegalArgumentException::class.java) {
                    goodsService.makeGoodsBom(상품번호, listOf(GoodsParts(Product(구성제품아이디1, "커피", 10), 구성제품1수량1), GoodsParts(Product(구성제품아이디2, "커피", 10), 구성제품2수량2)))
                }
                assertEquals("일부 제품이 존재하지 않습니다.", exception.message)
            }

            Then("서비스에 호출하는 메서드 확인") {
                verify(exactly = 2) { productRepository.findAll() }//필터를 해도 한번더 호출 되나 보다.
            }
        }

        When("구성 제품이 모두 있으면"){
            var goodsBom = GoodsBom(구성제품1수량1)
            goodsBom.goodsId = 상품번호
            goodsBom.productId = 구성제품아이디1

            var goodsBom2 = GoodsBom(구성제품2수량2)
            goodsBom2.goodsId = 상품번호
            goodsBom2.productId = 구성제품아이디2

            every{productRepository.findAll()} returns listOf(Product(구성제품아이디1, "커피", 10), Product(구성제품아이디2, "커피", 10))
            every{goodsBomRepository.save(goodsBom)} returns goodsBom
            every{goodsBomRepository.save(goodsBom2)} returns goodsBom2
            every{goodsBomRepository.findByGoodsId(상품번호)} returns listOf(GoodsBom(구성제품1수량1), GoodsBom(구성제품2수량2))

            Then("구성제품을 만든다"){
                val goodsBomList = goodsService.makeGoodsBom(상품번호, listOf(GoodsParts(Product(구성제품아이디1, "커피", 10), 구성제품1수량1), GoodsParts(Product(구성제품아이디2, "커피", 10), 구성제품2수량2)))
                goodsBomList.size shouldBe 2
            }

            Then("서비스에 호출하는 메서드 확인") {
                verify(exactly = 3) { productRepository.findAll() }
                verify(exactly = 2) { goodsBomRepository.save(any()) }
            }
        }

  }

})
