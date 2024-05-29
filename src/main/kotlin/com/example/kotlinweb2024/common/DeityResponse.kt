package com.example.kotlinweb2024.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class DeityResponse<T>(
    val message:String,
    var data:T?
) {

    companion object {
        fun <T> success(data: T): ResponseEntity<DeityResponse<T>> {
            return ResponseEntity.ok(DeityResponse<T>("OK", data))
        }

        fun success(): ResponseEntity<DeityResponse<String>> {
            return ResponseEntity.ok(DeityResponse<String>("OK", null))
        }
    }
}

class DeityError(
    val code:String,
    val message:String
){
    companion object {
        fun error(message:String): ResponseEntity<DeityError> {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DeityError(HttpStatus.INTERNAL_SERVER_ERROR.name, message))
        }
        fun error(code:String, message:String): ResponseEntity<DeityError> {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DeityError(code, message))
        }

        fun error(httpStatus:HttpStatus, code:String, message:String): ResponseEntity<DeityError> {
            return ResponseEntity.status(httpStatus).body(DeityError(code, message))
        }
    }
}