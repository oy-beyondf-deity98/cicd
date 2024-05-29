package com.example.kotlinweb2024.common

class DeityValidationException(message: String?) : RuntimeException() {
    constructor() : this(message = "잘못된 값 입력 됨")
}