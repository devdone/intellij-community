package com.jetbrains.kotlin.structuralsearch.replace

import com.jetbrains.kotlin.structuralsearch.KotlinSSRReplaceTest

class KotlinSSRShortenFqNamesTest : KotlinSSRReplaceTest() {
    fun testPropertyTypeShortenFQReplacement() {
        doTest(
            searchPattern = "var '_ID : '_TYPE",
            replacePattern = "var '_ID : java.io.File)",
            match = "fun main() { var foo: String }",
            result = """
                import java.io.File
                
                fun main() { var foo : File }
            """.trimIndent(),
            shortenFqNames = true
        )
    }

    fun testPropertyTypeNoShortenFQReplacement() {
        doTest(
            searchPattern = "var '_ID : '_TYPE",
            replacePattern = "var '_ID : java.io.File)",
            match = "fun main() { var foo: String }",
            result = "fun main() { var foo : java.io.File }",
        )
    }
}