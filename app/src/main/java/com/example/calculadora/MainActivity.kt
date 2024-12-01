package com.example.calculadora

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora.databinding.ActivityMainBinding
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentNumber = "0"
    private var previousNumber = 0.0
    private var currentOperator: String? = null
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBasicCalculator()
        if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            setupScientificCalculator()
        }
    }

    private fun setupBasicCalculator() {
        binding.apply {
            val buttons = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
            buttons.forEachIndexed { index, button ->
                button?.setOnClickListener { appendNumber(index.toString()) }
            }

            btnAdd?.setOnClickListener { setOperator("+") }
            btnSubtract?.setOnClickListener { setOperator("-") }
            btnMultiply?.setOnClickListener { setOperator("*") }
            btnDivide?.setOnClickListener { setOperator("/") }

            btnEqual?.setOnClickListener { calculateResult() }
            btnClear?.setOnClickListener { clearAll() }
        }
    }

    private fun setupScientificCalculator() {
        binding.apply {
            btnSin?.setOnClickListener { applyScientificFunction("sin") }
            btnCos?.setOnClickListener { applyScientificFunction("cos") }
            btnTan?.setOnClickListener { applyScientificFunction("tan") }
            btnLog?.setOnClickListener { applyScientificFunction("log") }
            btnLn?.setOnClickListener { applyScientificFunction("ln") }
            btnSqrt?.setOnClickListener { applyScientificFunction("sqrt") }
        }
    }

    private fun appendNumber(number: String) {
        if (isNewInput) {
            currentNumber = number
            isNewInput = false
        } else {
            currentNumber += number
        }
        updateDisplay(currentNumber)
    }

    private fun setOperator(operator: String) {
        try {
            if (currentOperator != null) {
                calculateResult()
            } else {
                previousNumber = currentNumber.toDouble()
            }
            currentOperator = operator
            isNewInput = true
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateResult() {
        try {
            if (currentOperator != null) {
                val current = currentNumber.toDouble()
                val result = when (currentOperator) {
                    "+" -> previousNumber + current
                    "-" -> previousNumber - current
                    "*" -> previousNumber * current
                    "/" -> {
                        if (current == 0.0) {
                            Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                            return
                        }
                        previousNumber / current
                    }
                    else -> current
                }
                updateDisplay(result.toString())
                previousNumber = result
                currentNumber = result.toString()
                currentOperator = null
                isNewInput = true
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun applyScientificFunction(function: String) {
        try {
            val number = currentNumber.toDouble()
            val result = when (function) {
                "sin" -> sin(Math.toRadians(number))
                "cos" -> cos(Math.toRadians(number))
                "tan" -> tan(Math.toRadians(number))
                "log" -> log10(number)
                "ln" -> ln(number)
                "sqrt" -> sqrt(number)
                else -> 0.0
            }
            updateDisplay(result.toString())
            currentNumber = result.toString()
            isNewInput = true
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearAll() {
        currentNumber = "0"
        previousNumber = 0.0
        currentOperator = null
        isNewInput = true
        updateDisplay("0")
    }

    private fun updateDisplay(value: String) {
        binding.txtResult?.text = value
    }
}
