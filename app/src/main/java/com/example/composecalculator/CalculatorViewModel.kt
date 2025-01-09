package com.example.composecalculator

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: Action) {
        Log.d(TAG, "Action received: $action")
        when (action) {
            is Action.Number -> enterNumber(action.number)
            is Action.Clear -> state = CalculatorState()
            is Action.Operation -> enterOperation(action.operation)
            is Action.Decimal -> enterDecimal()
            is Action.Calculate -> performCalculation()
            is Action.Delete -> performDeletion()
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.number1.length >= MAX_LENGTH) {
                return
            }
            state = state.copy(number1 = state.number1 + number)
            Log.d(TAG, "State updated: $state")
            Log.d(TAG, "Entered number: $number")
            return
        }

        if (state.number2.length >= MAX_LENGTH) {
            return
        }
        state = state.copy(number2 = state.number2 + number)
        Log.d(TAG, "State updated: $state")
        Log.d(TAG, "Entered number: $number")
        return

    }

    private fun enterOperation(operation: Operation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(number1 = state.number1 + ".")
            return
        }
        if (state.operation == null && !state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(number2 = state.number2 + ".")
            return
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull() ?: return
        val number2 = state.number2.toDoubleOrNull() ?: return
        val result = when(state.operation){
            is Operation.Add -> number1 + number2
            is Operation.Subtract -> number1 - number2
            is Operation.Multiply -> number1 * number2
            is Operation.Divide -> number1 / number2
            null -> return

        }
        state = CalculatorState(number1 = result.toString().take(15), number2 = "", operation = null)
    }



    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(number2 = state.number2.dropLast(1))
            state.operation != null -> state = state.copy(operation = null)
            state.number1.isNotBlank() -> state = state.copy(number1 = state.number1.dropLast(1))
        }
    }

    companion object {
        const val TAG = "CalculatorViewModel"
        const val MAX_LENGTH = 8;
    }


}