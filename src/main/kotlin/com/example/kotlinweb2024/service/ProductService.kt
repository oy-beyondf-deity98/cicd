package com.example.kotlinweb2024.service

import com.example.kotlinweb2024.domain.entity.Product

interface ProductService{
   fun addProduct(id: String, quantity: Int): Product
   fun removeProduct(id: String)
   fun newMakeProduct(id: String, name:String, quantity:Int):Product
   fun searchProduct(id: String): Product
   fun displayProducts(): List<Product>
}
