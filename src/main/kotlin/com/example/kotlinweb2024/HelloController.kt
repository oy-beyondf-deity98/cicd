package com.example.kotlinweb2024

import com.example.kotlinweb2024.common.DeityInternalException
import com.example.kotlinweb2024.common.DeityResponse
import com.example.kotlinweb2024.common.DeityValidationException
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/hello")
@RestController
@Tag(name = "Controller sample", description = "Controller 리턴값 Sample API")
class HelloController {

    @GetMapping("/world")
    fun hello():String{
        return "hello world!"
    }

    @GetMapping("/success/void")
    fun successResponse(): ResponseEntity<DeityResponse<String>> {
        return DeityResponse.success()
    }

    @GetMapping("/success/list")
    fun successResponseList(): ResponseEntity<DeityResponse<List<String>>> {
        return DeityResponse.success(listOf("success1", "success2"))
    }

    @GetMapping("/success/object")
    fun successResponseByObject(): ResponseEntity<DeityResponse<Map<String, String>>> {
        return DeityResponse.success(mapOf("key1" to "value1", "key2" to "value2"))
    }

    fun errorResponseBy404(){

    }

    @GetMapping("/error/system")
    fun errorResponseBySystem(){
        throw Exception("error")
    }

    @GetMapping("/error/custom/system")
    fun errorResponseByCustomSystem(){
        throw DeityInternalException("5321, error")
    }

    @GetMapping("/error/custom/validation")
    fun errorResponseByCustomValidation(){
        throw DeityValidationException("4321, error")

    }

    //어떻게 보여지는가? 실제값은?
    @GetMapping("/error/notfound")
    fun errorResponseByNotFound(){
        throw Exception("404, error")
    }


}