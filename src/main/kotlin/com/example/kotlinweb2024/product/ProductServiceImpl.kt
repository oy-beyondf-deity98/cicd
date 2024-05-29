package com.example.kotlinweb2024.product

import com.example.kotlinweb2024.domain.entity.Product
import com.example.kotlinweb2024.repository.ProductRepository
import com.example.kotlinweb2024.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository
) : ProductService {
    override fun addProduct(id: String, quantity: Int): Product {

        val findById = productRepository.findById(id)

        if (findById.isEmpty) {
            throw IllegalArgumentException("상품이 존재하지 않습니다.")
        }

        var findProduct = findById.get();

        findProduct.quantity += quantity

        productRepository.save(findProduct)
        return findProduct
    }

    override fun removeProduct(id: String) {
        productRepository.deleteById(id)
    }

    override fun newMakeProduct(id: String, name:String, quantity:Int): Product {
        val product = Product(id, name, quantity)
        return productRepository.save(product)
    }

    override fun searchProduct(id: String): Product {
        val findProduct = productRepository.findById(id)
        if(findProduct.isEmpty) {
            throw IllegalArgumentException("상품이 존재하지 않습니다.")
        }
        return findProduct.get()
    }

    override fun displayProducts(): List<Product> {
        val findProductList = productRepository.findAll()

        if(findProductList.isEmpty()) {
            throw IllegalArgumentException("상품이 존재하지 않습니다.")
        }
        return findProductList
    }
}