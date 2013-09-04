package com.vsa.filmoteca.utils;

import java.util.*;

public class StringUtils {

  private StringUtils() {}

  private static HashMap<String,String> htmlEntities;
  static {
    htmlEntities = new HashMap<String,String>();
    htmlEntities.put("&lt;","<")    ; htmlEntities.put("&gt;",">");
    htmlEntities.put("&amp;","&")   ; htmlEntities.put("&quot;","\"");
    htmlEntities.put("&agrave;","Ã "); htmlEntities.put("&Agrave;","Ã€");
    htmlEntities.put("&acirc;","Ã¢") ; htmlEntities.put("&auml;","Ã¤");
    htmlEntities.put("&Auml;","Ã„")  ; htmlEntities.put("&Acirc;","Ã‚");
    htmlEntities.put("&aring;","Ã¥") ; htmlEntities.put("&Aring;","Ã…");
    htmlEntities.put("&aelig;","Ã¦") ; htmlEntities.put("&AElig;","Ã†" );
    htmlEntities.put("&ccedil;","Ã§"); htmlEntities.put("&Ccedil;","Ã‡");
    htmlEntities.put("&eacute;","Ã©"); htmlEntities.put("&Eacute;","Ã‰" );
    htmlEntities.put("&egrave;","Ã¨"); htmlEntities.put("&Egrave;","Ãˆ");
    htmlEntities.put("&ecirc;","Ãª") ; htmlEntities.put("&Ecirc;","ÃŠ");
    htmlEntities.put("&euml;","Ã«")  ; htmlEntities.put("&Euml;","Ã‹");
    htmlEntities.put("&iuml;","Ã¯")  ; htmlEntities.put("&Iuml;","Ã?");
    htmlEntities.put("&ocirc;","Ã´") ; htmlEntities.put("&Ocirc;","Ã”");
    htmlEntities.put("&ouml;","Ã¶")  ; htmlEntities.put("&Ouml;","Ã–");
    htmlEntities.put("&oslash;","Ã¸") ; htmlEntities.put("&Oslash;","Ã˜");
    htmlEntities.put("&szlig;","ÃŸ") ; htmlEntities.put("&ugrave;","Ã¹");
    htmlEntities.put("&Ugrave;","Ã™"); htmlEntities.put("&ucirc;","Ã»");
    htmlEntities.put("&Ucirc;","Ã›") ; htmlEntities.put("&uuml;","Ã¼");
    htmlEntities.put("&Uuml;","Ãœ")  ; htmlEntities.put("&nbsp;"," ");
    htmlEntities.put("&copy;","\u00a9");
    htmlEntities.put("&reg;","\u00ae");
    htmlEntities.put("&euro;","\u20a0");
  }

/*
   Here the original recursive version.
   It is fine unless you pass a big string then a Stack Overflow is possible :-(


  public static final String unescapeHTML(String source, int start){
     int i,j;

     i = source.indexOf("&", start);
     if (i > -1) {
        j = source.indexOf(";" ,i);
        if (j > i) {
           String entityToLookFor = source.substring(i , j + 1);
           String value = (String)htmlEntities.get(entityToLookFor);
           if (value != null) {
             source = new StringBuffer().append(source.substring(0 , i))
                                   .append(value)
                                   .append(source.substring(j + 1))
                                   .toString();
             return unescapeHTML(source, i + 1); // recursive call
           }
         }
     }
     return source;
  }

   M. McNeely Jr. has sent a version with do...while()loop which is more robust.
   Thanks to him!
*/

  public static final String unescapeHTML(String source) {
      int i, j;

      boolean continueLoop;
      int skip = 0;
      do {
         continueLoop = false;
         i = source.indexOf("&", skip);
         if (i > -1) {
           j = source.indexOf(";", i);
           if (j > i) {
             String entityToLookFor = source.substring(i, j + 1);
             String value = (String) htmlEntities.get(entityToLookFor);
             if (value != null) {
               source = source.substring(0, i)
                        + value + source.substring(j + 1);
               continueLoop = true;
             }
             else if (value == null){
                skip = i+1;
                continueLoop = true;
             }
           }
         }
      } while (continueLoop);
      return source;
  }
  
  public static String removeAccents(String input) {
      // Cadena de caracteres original a sustituir.
      String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
      // Cadena de caracteres ASCII que reemplazarÃ¡n los originales.
      String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
      String output = input;
      for (int i=0; i<original.length(); i++) {
          // Reemplazamos los caracteres especiales.
          output = output.replace(original.charAt(i), ascii.charAt(i));
      }//for i
      return output;
  }
}
