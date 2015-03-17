package com.vsa.filmoteca.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by seldon on 17/03/15.
 */
public class PageParser {

    public static List<HashMap<String,String>> parseMoviesList(String html){
        ArrayList<HashMap<String,String>> moviesList=new ArrayList<HashMap<String,String>>();
        String titulo,subtitulo,fecha,url;

        if(html=="" || html==null){
            return new ArrayList<HashMap<String,String>>();
        }
        try {
            html = StringUtils.unescapeHTML(html);
            HashMap<String, String> Item;
            while (html.indexOf("url\">") > 0) {
                Item = new HashMap<String, String>();
                //Parsing sinopsis url
                html = html.substring(html.indexOf("Evento"));
                html = html.substring(html.indexOf("href=\"") + 6);
                url = html.substring(0, html.indexOf("\""));
                //Parsing title
                html = html.substring(html.indexOf("url\">") + 5, html.length());
                titulo = html.substring(0, html.indexOf("</a>"));
                if (titulo.indexOf("(") > 0) {
                    titulo = html.substring(0, titulo.indexOf("("));
                    subtitulo = html.substring(html.indexOf("("));
                } else {
                    subtitulo = "";
                }

                //Date parsing
                html = html.substring(html.indexOf("description\">") + 13, html.length());
                fecha = "- " + html.substring(0, html.indexOf("</span>"));
                //Delete whitespaces at the end
                while (fecha.substring(fecha.length() - 1).equals(" ") ||
                        fecha.substring(fecha.length() - 1).equals('\b') ||
                        fecha.substring(fecha.length() - 1).equals('\t') ||
                        fecha.substring(fecha.length() - 1).equals('\r')) {
                    fecha = fecha.substring(0, fecha.length() - 1);
                }


                titulo = titulo.trim();
                //Add the information to the hashmap
                Item.put(Constants.PARAM_ID_TITULO, "\t" + titulo);
                Item.put(Constants.PARAM_ID_SUBTITULO, subtitulo);
                Item.put(Constants.PARAM_ID_FECHA, fecha);
                Item.put(Constants.PARAM_ID_URL, url);
                //Add the hashmap to the arraylist
                moviesList.add(Item);

            }
        } catch (Exception e) {
            return new ArrayList<HashMap<String,String>>();
        }
        return moviesList;
    }

    public static String parseDetailPage(String html){
        String res;
        if(html==null){
            return html;
        }
        //Style
        String style="<style type=\"text/css\">img{ max-width:100%!important; height:auto!important;} strong{font-size:13px;} " +
                //"*{background-color:#f3f3f3!important;}"+
                "a{font-size:15px!important;}"+
                "p{text-align:center;}"+
                ".documentDescription{font-weight:bold;color:#000000; text-align:center;}"+
                ".tablaeventos table{ width:100%!important;}"+
                ".tablaeventos td{width:50%!important;}"+
                "a {color:#000000; font-weight:bold;}"+
                ".vevent{font-size:15px!important; color:#5f5c5c;}"+
                "td{ font-size:15px!important;line-height:18px; vertical-align:top;}"+
                "table{border:1px solid #848484;}"+
                "th{float:left!important;font-size:13px!important;}</style>";

        //Parseando Info
        html=html.substring(html.indexOf("<div class=\"vevent\">"));
        res=style+html.substring(0,html.indexOf("<div class=\"relatedItems\">"));
        res=res.replaceAll("\\<th", "<td");
        res=res.replaceAll("<\\/th", "</td");
        return res;
    }
}
