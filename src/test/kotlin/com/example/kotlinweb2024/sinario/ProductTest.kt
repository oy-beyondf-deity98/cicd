package com.example.kotlinweb2024.sinario

import com.example.kotlinweb2024.service.ProductService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ProductTest(
    @Autowired val productService: ProductService
) : DescribeSpec() {

    init {
        describe("Product") {
            context("만들기"){

                it("제품이 없을때 10개를 추가하면"){
                    val id = "B1104"
                    val name="코카콜라"
                    val quantity=10
//                    productService.removeProduct(id)

                    productService.newMakeProduct(id, name, quantity)   //초기화

                    var saveProduct = productService.searchProduct(id)

                    saveProduct.quantity shouldBe 10

                }
                it("제품이 10개 기존에 있을떄 10개를 추가하면"){
                    //given
                    val id = "B1104"
                    val name="코카콜라"
                    val quantity=10
                    productService.newMakeProduct(id,name,quantity)
                    val addQuantity=10

                    //when
                    var saveProduct = productService.addProduct(id, addQuantity);
                    println("saveProduct = ${saveProduct.quantity}")

                    //then
                    saveProduct.quantity shouldBe 20
                }
            }
            context("이후에 확인할 것"){
                it("일괄적인 가격의 제품"){

                }

                it("일괄적이지 않는 가격의 제품"){

                }

                it("제품의 가격이 일괄적이지 않을떄는 어떻게 하는가?"){

                }

                it("제품 분해"){
                    //제작한 가격은 안나온다. 일괄적인 가격이 나온다.
                }

            }

        }
    }

}

