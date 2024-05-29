package com.example.kotlinweb2024.common

import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//exception 코드값을 넘길수가 있는가? 에로코드를 구분자로 넘길수도 있다.
@RestControllerAdvice
class ExceptionAdvice {



    @ExceptionHandler(DeityValidationException::class)
    fun handleValidationException(e: DeityValidationException): ResponseEntity<DeityError> {
        return DeityError.error(HttpStatus.BAD_REQUEST,"4000", e.message ?: "error")
    }

    @ExceptionHandler(DeityInternalException::class)
    fun handleInternalException(e: DeityInternalException): ResponseEntity<DeityError> {
        println(e.message)
        return DeityError.error("5000",e.message ?: "error")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<DeityError> {
        println(e.message)
        return DeityError.error(e.message ?: "error")
    }
}