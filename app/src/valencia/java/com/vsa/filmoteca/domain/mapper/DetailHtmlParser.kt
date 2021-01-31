package com.vsa.filmoteca.domain.mapper

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by seldon on 9/04/15.
 */
object DetailHtmlParser {

    private const val ID_HEADER = "cabecera"
    private const val ID_MENU_AREA = "zona-menu"
    private const val ID_FOOTER = "pie"
    private const val ID_FOOTER_ICONS = "logospie"
    private const val ID_BREADCRUMB = "miguitas"

    private const val CLASS_FRAME = "franja"
    private const val CLASS_INTERNAL = "interno"
    private const val CLASS_PICTURES_BLOCK = "bloque-fotos"
    private const val CLASS_TEXTS_BLOCK = "bloque-textos"
    private const val CLASS_SOCIAL_NETWORKS = "enlaces-redes"

    private const val TAG_H1 = "h1"

    fun parse(source: String?): String? {
        if (source == null)
            return null

        val style = "<style type=\"text/css\">" +
                ".interno{ padding-top: 0px!important; margin-top: 0px!important; } " +
                ".separador-40{ padding: 0px!important; margin: 0px!important; } " +
                "</style>"

        val document = Jsoup.parse(source)
        document.getElementById(ID_HEADER).remove()
        document.getElementById(ID_MENU_AREA).remove()
        removeBreadCrumb(document)
        document.getElementsByTag(TAG_H1).remove() //Remove movie title
        document.getElementsByClass(CLASS_SOCIAL_NETWORKS).remove()
        document.getElementById(ID_FOOTER).remove()
        document.getElementById(ID_FOOTER_ICONS).remove()

        return style + document.html()
    }

    private fun removeBreadCrumb(document: Document) {
        document.getElementsByClass(CLASS_FRAME).forEach { element ->
            if (element.getElementById(ID_BREADCRUMB) != null)
                element.remove()
        }
    }


}
