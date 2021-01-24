package com.vsa.filmoteca.domain.mapper

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import java.util.*

/**
 * Created by seldon on 9/04/15.
 */
object DetailHtmlParser {

    private const val CLASS_TABLAEVENTOS = "tablaeventos"
    private const val CLASS_VEVENT = "vevent"

    private const val TAG_DD = "dd"
    private const val TAG_A = "a"
    private const val TAG_IMG = "img"

    private const val ATTR_STYLE = "style"
    private const val ATTR_HREF = "href"


    fun parse(source: String?): String? {
        if (source == null)
            return null
        //Style
        val style = "<style type=\"text/css\">" +
                "img, input{ max-width:100%!important; height:auto!important;} strong{font-size:13px;} " +
                "@font-face {\n" +
                "    font-family: Monserrat;\n" +
                "    src: url(\"file:///android_asset/fonts/montserrat_regular.ttf\")\n" +
                "} " +
                "body {\n" +
                "    font-family: Monserrat;\n" +
                "    font-size: medium;\n" +
                "    text-align: justify;\n" +
                "}" +
                "a{font-size:15px!important;" +
                "word-wrap: break-word; /* Internet Explorer 5.5+ */ " +
                "}" +
                "p{text-align:center;}" +
                ".documentDescription{font-weight:bold;color:#000000; text-align:center;}" +
                ".tablaeventos table{ width:100%!important;}" +
                ".tablaeventos td{width:50%!important;}" +
                "a {color:#000000; font-weight:bold;}" +
                ".vevent{font-size:15px!important; color:#5f5c5c;}" +
                "td{ font-size:15px!important;line-height:18px; vertical-align:top;}" +
                "table{border:1px solid #848484;}" +
                "dd{ margin: 8px 5px 8px 105px;" +
                "color: rgb(51, 51, 51); " +
                "font-size: 14px; line-height: 18px; font-family: Arial, sans-serif; font-style: normal;" +
                "font-variant: normal; font-weight: normal; letter-spacing: normal; " +
                "orphans: auto; text-align: start; text-indent: 0px; text-transform: none; " +
                "white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; " +
                "background-color: rgb(255, 255, 255); " +
                "}" +
                "th{float:left!important;font-size:13px!important;}</style>"

        val document = Jsoup.parse(source)
        document.getElementsByTag(TAG_DD).removeAttr(ATTR_STYLE)
        document.getElementsByTag(TAG_A).removeAttr(ATTR_HREF)

        var vevent = document.getElementsByClass(CLASS_VEVENT).first()

        val tablaEventosList = vevent.getElementsByClass(CLASS_TABLAEVENTOS)
        val tablaEventos: Element

        if (tablaEventosList!!.isEmpty()) {
            tablaEventos = document.getElementsByClass(CLASS_TABLAEVENTOS).first()
            val nodesToInsert = ArrayList<Node>()
            nodesToInsert.add(tablaEventos)
            vevent = vevent.insertChildren(vevent.childNodeSize(), nodesToInsert)
        } else {
            tablaEventos = tablaEventosList.first()
        }

        if (tablaEventosList.isNotEmpty()) {
            val tablaEventosIcons = tablaEventos.getElementsByTag(TAG_IMG)
            tablaEventosIcons.remove()
        }

        val parsedHTML = vevent.html()

        return style + parsedHTML
    }

}
