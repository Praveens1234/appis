package com.appis.android.domain.engine

import org.junit.Assert.*
import org.junit.Test

class OutputValidatorTest {

    private val validator = OutputValidator()

    @Test
    fun `validateKotlin returns true for balanced braces`() {
        val code = "fun main() { println(\"Hello\") }"
        assertTrue(validator.validateKotlin(code))
    }

    @Test
    fun `validateKotlin returns false for unbalanced braces`() {
        val code = "fun main() { println(\"Hello\")"
        assertFalse(validator.validateKotlin(code))
    }

    @Test
    fun `validateKotlin handles nested braces`() {
        val code = "class A { fun b() { if(true) { } } }"
        assertTrue(validator.validateKotlin(code))
    }
}
