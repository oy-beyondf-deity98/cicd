package com.example.kotlinweb2024.common

import java.rmi.ServerException

class DeityInternalException(s: String?) : ServerException(s) {
    constructor() : this(s  = "Server error")
}