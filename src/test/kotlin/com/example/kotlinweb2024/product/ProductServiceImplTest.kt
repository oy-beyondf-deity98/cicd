package com.example.kotlinweb2024.product

import com.example.kotlinweb2024.domain.entity.Product
import com.example.kotlinweb2024.repository.ProductRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class ProductServiceImplTest: BehaviorSpec({
    val productRepository = mockk<ProductRepository>()
    val productService = ProductServiceImpl(productRepository)

    Given("entity 확인 테스트") {
        val productId = "1"
        val product = Product(productId, "커피", 10)
        every{productRepository.save(any())} returns product

        When("제품을 만들면") {
            val saveProduct = productService.newMakeProduct(productId, "커피", 10)

            Then("다시 조회된것과 저장되기 전의 데이터는 동기화된다. 그래서 같은 값으로 취급된다.") {
                saveProduct shouldBe product
            }
        }
    }

    Given("제품이 없을때") {
        val productId = "1"
//        val product = Product(productId, "커피", 10)
        every{productRepository.findById(productId)} returns Optional.empty()
        every{productRepository.findAll()} returns listOf()

        When("제품을 추가하면") {

            Then("오류가 발생한다.") {
                val exception = io.kotest.assertions.throwables.shouldThrow<IllegalArgumentException> {
                    productService.addProduct(productId, 10)
                }
                exception.message shouldBe "상품이 존재하지 않습니다."
            }

            Then("서비스에 호출하는 메스드 확인") {
                verify(exactly = 1) { productRepository.findById(productId) }
                verify(exactly = 1) { productRepository.save(any()) }//왜 오류나는데 호출하는가? 호출이 안되야 하는거 아닌가? 그냥 단순히 메서드에서 호출해서 그런가? 호출하고나서 오류나는것 같다.
            }
        }

        When("제품을 조회하면"){
            Then("오류가 발생한다"){
                val exception = io.kotest.assertions.throwables.shouldThrow<IllegalArgumentException> {
                    productService.searchProduct(productId)
                }
                exception.message shouldBe "상품이 존재하지 않습니다."
            }

            Then("서비스에 호출하는 메스드 확인"){
                verify(exactly = 2) { productRepository.findById(productId) }//이건 잘못된것 아닌가?
            }
        }

        When("제품을 전시하면"){
            Then("오류가 발생한다"){
                val exception = io.kotest.assertions.throwables.shouldThrow<IllegalArgumentException> {
                    productService.displayProducts()
                }
                exception.message shouldBe "상품이 존재하지 않습니다."
            }

            Then("서비스에 호출하는 메스드 확인"){
                verify(exactly = 1) { productRepository.findAll() }
            }
        }
    }
})
