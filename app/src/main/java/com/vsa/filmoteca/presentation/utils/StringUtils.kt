package com.vsa.filmoteca.presentation.utils

import java.util.*

object StringUtils {

    //http://www.rgagnon.com/javadetails/java-0307.html
    //http://www.v3rgu1.com/blog/231/2010/programacion/eliminar-acentos-y-caracteres-especiales-en-java/

    private var htmlEntities: HashMap<String, String>? = null

    init {
        htmlEntities = HashMap()
        htmlEntities!!["&lt;"] = "<"
        htmlEntities!!["&gt;"] = ">"
        htmlEntities!!["&amp;"] = "&"
        htmlEntities!!["&quot;"] = "\""
        htmlEntities!!["&agrave;"] = "à"
        htmlEntities!!["&Agrave;"] = "À"
        htmlEntities!!["&acirc;"] = "â"
        htmlEntities!!["&auml;"] = "ä"
        htmlEntities!!["&Auml;"] = "Ä"
        htmlEntities!!["&Acirc;"] = "Â"
        htmlEntities!!["&aring;"] = "å"
        htmlEntities!!["&Aring;"] = "Å"
        htmlEntities!!["&aelig;"] = "æ"
        htmlEntities!!["&AElig;"] = "Æ"
        htmlEntities!!["&ccedil;"] = "ç"
        htmlEntities!!["&Ccedil;"] = "Ç"
        htmlEntities!!["&eacute;"] = "é"
        htmlEntities!!["&Eacute;"] = "É"
        htmlEntities!!["&egrave;"] = "è"
        htmlEntities!!["&Egrave;"] = "È"
        htmlEntities!!["&ecirc;"] = "ê"
        htmlEntities!!["&Ecirc;"] = "Ê"
        htmlEntities!!["&euml;"] = "ë"
        htmlEntities!!["&Euml;"] = "Ë"
        htmlEntities!!["&iuml;"] = "ï"
        htmlEntities!!["&Iuml;"] = "Ï"
        htmlEntities!!["&ocirc;"] = "ô"
        htmlEntities!!["&Ocirc;"] = "Ô"
        htmlEntities!!["&ouml;"] = "ö"
        htmlEntities!!["&Ouml;"] = "Ö"
        htmlEntities!!["&oslash;"] = "ø"
        htmlEntities!!["&Oslash;"] = "Ø"
        htmlEntities!!["&szlig;"] = "ß"
        htmlEntities!!["&ugrave;"] = "ù"
        htmlEntities!!["&Ugrave;"] = "Ù"
        htmlEntities!!["&ucirc;"] = "û"
        htmlEntities!!["&Ucirc;"] = "Û"
        htmlEntities!!["&uuml;"] = "ü"
        htmlEntities!!["&Uuml;"] = "Ü"
        htmlEntities!!["&nbsp;"] = " "
        htmlEntities!!["&copy;"] = "\u00a9"
        htmlEntities!!["&reg;"] = "\u00ae"
        htmlEntities!!["&euro;"] = "\u20a0"
    }

    fun unescapeHTML(source: String): String {
        var source = source
        var i: Int
        var j: Int

        var continueLoop: Boolean
        var skip = 0
        do {
            continueLoop = false
            i = source.indexOf("&", skip)
            if (i > -1) {
                j = source.indexOf(";", i)
                if (j > i) {
                    val entityToLookFor = source.substring(i, j + 1)
                    val value = htmlEntities!![entityToLookFor]
                    if (value != null) {
                        source = (source.substring(0, i)
                                + value + source.substring(j + 1))
                        continueLoop = true
                    } else {
                        skip = i + 1
                        continueLoop = true
                    }
                }
            }
        } while (continueLoop)
        return source
    }

    fun removeAccents(input: String): String {
        val original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ"
        val ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC"
        var output = input
        for (i in 0 until original.length)
            output = output.replace(original[i], ascii[i])
        return output
    }

    fun capitalizeFirstCharacter(source: String?): String =
            source?.split(" ")
                    ?.joinToString(separator = " ") { it.capitalize() }
                    ?: ""


    fun removePunctuationSymbols(source: String?): String {
        return if (source == null || source.isEmpty()) "" else source.replace("\\p{Punct}+".toRegex(), "")
    }

    fun removeBlankSpaces(source: String?): String {
        return if (source == null || source.isEmpty()) "" else source.replace(" ", "")
    }

    fun isEmpty(string: String?): Boolean {
        return string == null || string.isEmpty()
    }

}
