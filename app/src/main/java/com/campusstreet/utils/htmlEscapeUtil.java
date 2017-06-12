package com.campusstreet.utils;

/**
 * Created by develop2 on 2017/6/12.
 */

public class htmlEscapeUtil {

    public static String htmlReplace(String str){
        str = str.replace("&quot;", "\"");
        str = str.replace("&apos;", "'");
        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");
        str = str.replace("&amp;", "&");
        str = str.replace("&rsquo;", "’");
        str = str.replace("&mdash;", "—");
        str = str.replace("&ndash;", "–");
        str = str.replace("&ldquo;", "“");
        str = str.replace("&rdquo;", "”");
        str = str.replace("&nbsp;", " ");
        str = str.replace("&#39;", "'");
        return str;
    }
}
