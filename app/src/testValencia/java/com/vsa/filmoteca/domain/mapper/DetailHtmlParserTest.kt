package com.vsa.filmoteca.domain.mapper

import org.junit.Test

class DetailHtmlParserTest {

    private val originalHtml: String? =
        this::class.java.getResourceAsStream("/Detail.html")?.reader()?.readText()

    private val parsedHtml: String? =
        this::class.java.getResourceAsStream("/ParsedDetail.html")?.reader()?.readText()

    @Test
    fun testThatTheDetailParsedDetailIsTheExpected() {
        assert(DetailHtmlParser.parse(originalHtml) == parsedHtml)
    }

}