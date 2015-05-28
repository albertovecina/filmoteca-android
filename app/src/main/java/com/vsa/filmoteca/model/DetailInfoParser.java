package com.vsa.filmoteca.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seldon on 9/04/15.
 */
public class DetailInfoParser {

    private static final String CLASS_TABLAEVENTOS = "tablaeventos";
    private static final String CLASS_VEVENT = "vevent";

    private static final String TAG_DD = "dd";
    private static final String TAG_A = "a";
    private static final String TAG_IMG = "img";

    private static final String ATTR_STYLE = "style";
    private static final String ATTR_HREF = "href";


    public static String parse(String source) throws Exception{
        if(source == null)
            return null;
        //Style
        String style="<style type=\"text/css\">" +
                "img, input{ max-width:100%!important; height:auto!important;} strong{font-size:13px;} " +
                "a{font-size:15px!important;" +
                "word-wrap: break-word; /* Internet Explorer 5.5+ */ "+
                "}"+
                "p{text-align:center;}"+
                ".documentDescription{font-weight:bold;color:#000000; text-align:center;}"+
                ".tablaeventos table{ width:100%!important;}"+
                ".tablaeventos td{width:50%!important;}"+
                "a {color:#000000; font-weight:bold;}"+
                ".vevent{font-size:15px!important; color:#5f5c5c;}"+
                "td{ font-size:15px!important;line-height:18px; vertical-align:top;}"+
                "table{border:1px solid #848484;}"+
                "dd{ margin: 8px 5px 8px 105px;" +
                "color: rgb(51, 51, 51); " +
                "font-size: 14px; line-height: 18px; font-family: Arial, sans-serif; font-style: normal;" +
                "font-variant: normal; font-weight: normal; letter-spacing: normal; " +
                "orphans: auto; text-align: start; text-indent: 0px; text-transform: none; " +
                "white-space: normal; widows: 1; word-spacing: 0px; -webkit-text-stroke-width: 0px; " +
                "background-color: rgb(255, 255, 255); " +
                "}" +
                "th{float:left!important;font-size:13px!important;}</style>";

        Document document = Jsoup.parse(source);
        document.getElementsByTag(TAG_DD).removeAttr(ATTR_STYLE);
        document.getElementsByTag(TAG_A).removeAttr(ATTR_HREF);

        Element vevent = document.getElementsByClass(CLASS_VEVENT).first();

        Elements tablaEventosList = vevent.getElementsByClass(CLASS_TABLAEVENTOS);
        Element tablaEventos;

        if(tablaEventosList.isEmpty()) {
            tablaEventos = document.getElementsByClass(CLASS_TABLAEVENTOS).first();
            List<Node> nodesToInsert = new ArrayList<>();
            nodesToInsert.add(tablaEventos);
            vevent = vevent.insertChildren(vevent.childNodeSize(), nodesToInsert);
        } else {
            tablaEventos = tablaEventosList.first();
        }

        if(tablaEventosList != null) {
            Elements tablaEventosIcons = tablaEventos.getElementsByTag(TAG_IMG);
            tablaEventosIcons.remove();
        }

        String parsedHTML = vevent.html();

        return style + parsedHTML;
    }

}
