package com.vsa.filmoteca.data.domain.mapper;

import com.vsa.filmoteca.presentation.utils.StringUtils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by albertovecinasanchez on 7/11/16.
 */

public class StringUtilsTest {

    @Test
    public void stringUtils_capitalizeFirstLetter() {
        String testText = "RETRATO / ENTREVISTA A JOSÉ LUIS CUERDA";
        testText = StringUtils.capitalizeFirstCharacter(testText);
        assertThat(Character.isUpperCase(testText.charAt(0)), is(true));

        testText = "RETRATO  ENTREVISTA A JOSÉ LUIS CUERDA";
        testText = StringUtils.capitalizeFirstCharacter(testText);
        assertThat(Character.isUpperCase(testText.charAt(0)), is(true));
    }

    @Test
    public void stringUtils_removeBlankSpaces() {
        String testText = "RETRATO / ENTREVISTA A JOSÉ LUIS CUERDA";
        testText = StringUtils.removeBlankSpaces(testText);
        assertThat(testText.contains(" "), is(false));
    }

}
