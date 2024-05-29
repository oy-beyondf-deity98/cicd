1. hello world
2. swagger ui 완성
   - http://localhost:7020/swagger-ui/index.html
3. controller response 만들기 
   - validation 체크를 명시적으로 진행하자.
   - 음.. 너무 많으면 그냥 하자.
4. 데이터 소스를 명시적으로 하기 : 명시적으로 하면 database 설정이 자동으로 된다. 개발이 편하다.
   - 어디까지 될까? yml도 될까? properties는 된다.
```markdown
spring.datasource.url=jdbc:mysql://localhost:3306/mydatabase
spring.datasource.username=myuser
spring.datasource.password=secret
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

---

설계는 요구사항을 받고 하는가 데이터 구조를 만들고 하는가?

코틀린에서 유명한 테스트 도구는 kotest, mockk 이다. 
- 이외에 모키토도 빠질수가 없겠지?
- https://techblog.woowahan.com/5825/



코틀린 테스트는 주로 kotest를 많이 쓴다. 그리고 스프링부트환경에서 테스트이기때문에 아래 두개는 넣어야 한다.
   -  kotest-runner-junit5는 안넣어도 되는지 확인이 필요하다.
   - 스프링부트 kotest와 kotest runner의 버전정보가 필요하다.
```gradle
    val kotestVersion = "5.8.1"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
```

### 인텔리J에서 테스트를 실행하려면 plugin을 설치해야 한다.
- kotest라고 검색하면 나온다. 간단하다. 설치하면 junit과 같이 검색 버튼이 나온다.
- 그전에도 옆에 모가 나오는데 어떻게 쓰는지 모르겠다.

## 오류발생
초기화 오류로 발생했다. class body에 해서 나온것으로 밝혀졌다.
- 다음과 같이 클래스의 ()사이에 설정을 해야한다.
   - 생각해보면 자바에서도 final로 선언해야 했었네.. 여기도 비슷한것 같다.
- @DataJpaTest를 사용하지 말자. 우선은 말이다.


## 여기서는 실제 데이터 들어가면서 넣을것이기 때문이다.
### 그래서 SpringBootTest를 사용하는 것이다.

실전에서는 각 서비스 단위로 mock을 만들어서 실제 데이터가 안돌아가도록 해야한다.
레파지토리를 가져와서 하다가 서비스로 하자.

```kotlin
@SpringBootTest
internal class ProductTest(
    @Autowired val productRepository: ProductRepository

) : DescribeSpec() {

    init {
        describe("Product") {
            it("제품 만들기") {

                val product = Product("1", "코카콜라", 1000, 10)
                productRepository.save(product)
//
                val savedProduct = productRepository.findById("1").get()
                savedProduct shouldBe product

            }
        }
    }
}

//다음과 같이 context로 그룹을 만들수 있다.
@SpringBootTest
internal class ProductTest(
    @Autowired val productRepository: ProductRepository

) : DescribeSpec() {

    init {
        describe("Product") {
            context("첫번쨰"){
                it("제품 만들기") {

                    val product = Product("1", "코카콜라", 1000, 10)
                    productRepository.save(product)
//
                    val savedProduct = productRepository.findById("1").get()
                    savedProduct shouldBe product

                }    
            }
            
            context("두번째") {
                it("제품 만들기") {

                    val product = Product("2", "코카콜라", 1000, 10)
                    productRepository.save(product)
                }
            }
        }
    }
}
```

자바스크립트의 테스트와 비슷한 이것은 무엇인가.. 이게 DSL인가? 아닌것 같은데 ㅎㅎ
- 이게 무슨 Domain Specific Language 야. 
- 그냥 자바스크립트 테스트 코드지.. 아니 자바스크립트 코드로 정립한것인가? ㅎㅎ


---

이후의 일 
1. 서비스 만들기
2. 제품 만들고 상품 만들고, 상품 팔기 까지 하기
   - @SpringBootTest로 진행
3. Mockk를 이용한 테스트 케이스 만들기
4. 테스트케이스의 DB는 따로 만들기
5. 개발서버 만들기 : docker
   - DB 따로 만들기
   - 개발서버와 로컬은 따로 놀기
   - 로컬 서버는 어디에?
   - @SpringBootTest로 했던것은 폐지하기


---

문제점 발생
왜 다음코드는 에러가 나는가? 
- 대충 이유는 짐작은 가는데 확답이 안보이네, 연관된 테이블을 만들기는 왜 만들어?
- goods가 있는데 다시 goods를 만드는 이유는 무엇인가?

```markdown 메세지
Hibernate: select g1_0.id,g1_0.goods_codes,g1_0.name,g1_0.price,g1_0.stock from goods g1_0 where g1_0.id=?
Hibernate: insert into goods (goods_codes,name,price,stock,id) values (?,?,?,?,?)
Hibernate: select p1_0.id,p1_0.name,p1_0.quantity from product p1_0 where p1_0.id=?
Hibernate: select p1_0.id,p1_0.name,p1_0.quantity from product p1_0 where p1_0.id=?
Hibernate: insert into product (name,quantity,id) values (?,?,?)
Hibernate: select p1_0.id,p1_0.name,p1_0.quantity from product p1_0 where p1_0.id=?
Hibernate: select p1_0.id,p1_0.name,p1_0.quantity from product p1_0 where p1_0.id=?
Hibernate: insert into product (name,quantity,id) values (?,?,?)
Hibernate: select gb1_0.goods_id,g1_0.id,g1_0.goods_codes,g1_0.name,g1_0.price,g1_0.stock,gb1_0.product_id,p1_0.id,p1_0.name,p1_0.quantity,gb1_0.quantity from goods_bom gb1_0 join goods g1_0 on g1_0.id=gb1_0.goods_id join product p1_0 on p1_0.id=gb1_0.product_id where (gb1_0.goods_id,gb1_0.product_id) in ((?,?))
Hibernate: select null,g1_0.goods_codes,g1_0.name,g1_0.price,g1_0.stock from goods g1_0 where g1_0.id=?
Hibernate: select null,p1_0.name,p1_0.quantity from product p1_0 where p1_0.id=?
Hibernate: insert into goods_bom (quantity,goods_id,product_id) values (?,?,?)
Hibernate: insert into goods (goods_codes,name,price,stock,id) values (?,?,?,?,?)

2024-05-16T19:55:07.244+09:00  WARN 5039 --- [kotlinWeb2024] [pool-1-thread-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1062, SQLState: 23000
2024-05-16T19:55:07.244+09:00 ERROR 5039 --- [kotlinWeb2024] [pool-1-thread-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : Duplicate entry 'G1101' for key 'goods.PRIMARY'
```

```kotlin
package com.example.kotlinweb2024.product

import com.example.kotlinweb2024.domain.entity.Goods
import com.example.kotlinweb2024.domain.entity.GoodsBom
import com.example.kotlinweb2024.domain.entity.Product
import com.example.kotlinweb2024.goods.GoodsBomRepository
import com.example.kotlinweb2024.goods.GoodsRepository
import com.example.kotlinweb2024.goods.ProductPurchaseRepository
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class GoodsTest(
   @Autowired val goodsBomRepository: GoodsBomRepository,
   @Autowired val productPurchaseRepository: ProductPurchaseRepository,
   @Autowired val goodsRepository: GoodsRepository,
   @Autowired val productRepository: ProductRepository,
   @Autowired val productService: ProductService

): DescribeSpec(){

   init{
      describe("Goods"){
         context("상품 구성"){
            it("상품 1은 제품1 10개과 제품2 1개로 구성해서 만든다."){
               //given

               //상품 1을 만든다.
               var saveGoods = 상품만들기()


               val (제품1, 제품2) = 제품만들기()


               var saveGoodsBom:GoodsBom = GoodsBom(10)
               saveGoodsBom.goods = saveGoods
               saveGoodsBom.product = 제품1

               var saveGoodsBom2:GoodsBom = GoodsBom(1)
               saveGoodsBom2.goods = saveGoods
               saveGoodsBom2.product = 제품2

               goodsBomRepository.save(saveGoodsBom)
               goodsBomRepository.save(saveGoodsBom2)

               //when

               //then




            }
         }
      }
   }


   fun 제품만들기(): Pair<Product, Product> {
      val product1Id = "B1104"
      val product1Name = "코카콜라"
      val product1Quantity = 10
      val product1 = Product(product1Id, product1Name, product1Quantity)

      val product2Id = "B1105"
      val product2Name = "펩시콜라"
      val product2Quantity = 1
      val product2 = Product(product2Id, product2Name, product2Quantity)

      val 제품1 = productService.addProduct(product1Id, product1Name, product1Quantity)
      val 제품2 = productService.addProduct(product2Id, product2Name, product2Quantity)
      return Pair(제품1, 제품2)
   }

   fun 상품만들기(): Goods {
      val goodsId = "G1101"
      val goodsName = "코카콜라"
      val price = 10000

      var makeGoods: Goods = Goods(goodsName, price)

      makeGoods.id = goodsId
      var saveGoods = goodsRepository.save(makeGoods)
      return saveGoods
   }
}
```


## 해결방법
그냥 String으로 연결하였다. 그게 편하기도하고.... 몬가 억울한가?
그런데 직접적으로 연결은 없는것이다.. 꼭 연결을 해야하는가? 음..

## 내일 할일
1. 상품을 구매하는 것을 만들어보자.
2. UI도 구상 해야한다. UI가 중요한거자나? 공장이나 상점을 만들어야지. 하고 싶은거 다~ 하자.


### 문제점
enum의 요소에 val을 안붙여주었더니, 해당요소를 따로 불러올수가 없네?? ㅎㅎㅎ


제목 : 오늘도 파멸이다~~
- 왜냐? 물건을 사두고는 남아서 어디다가 둘데가 없어서지므로 파멸이 된다. 

# 코딩하는 법
## 레파지토리로 입력테스트를 하고, 그 중에서 반복되는것을 service로 옮긴다.
### 레파지토리를 직접사용하는것을 서비스로 모두 옮긴다.
### 익숙한 한글로 함수를 만들어도 된다.
### 한글은 나중에 바꾸면 된다.
## 서비스에 대한 테스트를 만든다.
### exception에 대한 것을 만든다.
## controller을 만든다.
### api호출에서 나올것을 만든다.
### exception을 만든다.


---

이제는 무엇을 해야할까? 서비스도 모드 만든것 같은데? 서비스 테스트를 만들어야 할까? 아니 컨트롤러를 만들어야 겠지?
화면을 만들어야 하나?
무흐
공장을 만들자. 나의 공장, 아니 공장 이용자라고 해야하나? 어떤 시나리오를 써야할까? 컨트롤러를 만들고 테스트를 만들자.


마이크로에서는 패키지구조가 다르게 나뉘어도 될것 같다. 어딘가가 다르다고 해야하나?


실제 DB가 아닌것들로 만든다.

---
문제가 생겼다.. 테스트케이스를 어떻게 만드는 거였지? 응?


다음에는 더욱 익숙해지게 해보자. 더욱빠르게도 만들어야겠고말이다.. 숙달이 더 중요하다. 숙달되지 않는 프로그래밍은 죽은 프로그래밍이잖냐
아.. 내가 여태했던게 kotest구나


인터페이스에 대한 테스트는 BehaviorSpec로 해서 BDD가 편하고
실제 서비스에 대한 테스트는 DescribeSpec로 해서 테스트케이스를 만들어야겠다.
테스트 케이스는 성공사례보다는 실패사례가 더 많이 적히는것 같다.

```kotlin
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

         Then("만든 제품을 반환한다.") {
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
            verify(exactly = 1) { productRepository.save(any()) }//왜 오류나는데 호출하는가? 호출이 안되야 하는거 아닌가? 그냥 단순히 메서드에서 호출해서 그런가?
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
```



