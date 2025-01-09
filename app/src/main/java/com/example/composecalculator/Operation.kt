package com.example.composecalculator

sealed class Operation(val symbol: String) {
    object Add : Operation("+")
    object Subtract : Operation("-")

    object Divide : Operation("/")
    object Multiply : Operation("*")

}