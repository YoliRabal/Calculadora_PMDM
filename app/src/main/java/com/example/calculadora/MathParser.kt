package com.example.calculadora

import kotlin.math.*

class MathParser {
    fun evaluate(expression: String): Double {
        val tokens = expression.split(" ")
        var result = tokens[0].toDouble()
        var operator = ""

        for (token in tokens.drop(1)) {
            when (token) {
                "+" -> operator = "+"
                "-" -> operator = "-"
                "*" -> operator = "*"
                "/" -> operator = "/"
                else -> {
                    val value = token.toDouble()
                    when (operator) {
                        "+" -> result += value
                        "-" -> result -= value
                        "*" -> result *= value
                        "/" -> result /= value
                    }
                }
            }
        }
        return result
    }
}