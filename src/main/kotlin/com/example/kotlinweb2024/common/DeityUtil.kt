package com.example.kotlinweb2024.common

import java.util.*

class DeityUtil {

    companion object {
        fun makeId(): String {
            return UUID.randomUUID().toString()
        }
    }
}