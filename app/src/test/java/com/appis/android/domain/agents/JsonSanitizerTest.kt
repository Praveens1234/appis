package com.appis.android.domain.agents

import com.appis.android.utils.JsonSanitizer
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonSanitizerTest {

    @Test
    fun `sanitize extracts json from markdown block`() {
        val input = """
            Here is the plan:
            ```json
            { "key": "value" }
            ```
        """.trimIndent()
        
        val expected = """{ "key": "value" }"""
        assertEquals(expected, JsonSanitizer.sanitize(input))
    }

    @Test
    fun `sanitize extracts json from braces only`() {
        val input = """
            Sure, here:
            { "key": "value" }
        """.trimIndent()
        
        val expected = """{ "key": "value" }"""
        assertEquals(expected, JsonSanitizer.sanitize(input))
    }
}
