package com.example.kotlinweb2024

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class KotlinWeb2024Application

fun main(args: Array<String>) {
    runApplication<KotlinWeb2024Application>(*args)
}
