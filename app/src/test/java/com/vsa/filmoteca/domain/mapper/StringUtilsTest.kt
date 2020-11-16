package com.vsa.filmoteca.domain.mapper

import com.vsa.filmoteca.presentation.utils.StringUtils

import org.junit.Test

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`

/**
 * Created by albertovecinasanchez on 7/11/16.
 */

class StringUtilsTest {

    @Test
    fun stringUtils_capitalizeFirstLetter() {
        var testText = "RETRATO / ENTREVISTA A JOSÉ LUIS CUERDA"
        testText = StringUtils.capitalizeFirstCharacter(testText)
        assertThat(Character.isUpperCase(testText[0]), `is`(true))

        testText = "RETRATO  ENTREVISTA A JOSÉ LUIS CUERDA"
        testText = StringUtils.capitalizeFirstCharacter(testText)
        assertThat(Character.isUpperCase(testText[0]), `is`(true))
    }

    @Test
    fun stringUtils_removeBlankSpaces() {
        var testText = "RETRATO / ENTREVISTA A JOSÉ LUIS CUERDA"
        testText = StringUtils.removeBlankSpaces(testText)
        assertThat(testText.contains(" "), `is`(false))
    }

}
