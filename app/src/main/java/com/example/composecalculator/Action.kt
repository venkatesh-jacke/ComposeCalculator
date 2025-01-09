package com.example.composecalculator


sealed class Action{
    data class Number(val number:Int):Action()
    data class Operation(val operation: com.example.composecalculator.Operation):Action()
    object Clear:Action()
    object Delete:Action()
    object Decimal:Action()
    object Calculate:Action()

}