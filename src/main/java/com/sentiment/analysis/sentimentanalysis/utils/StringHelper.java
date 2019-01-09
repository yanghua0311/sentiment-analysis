package com.sentiment.analysis.sentimentanalysis.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
	/**
	 * 循环删除字符串中指定的两个字符之间的子串
	 * @param wbText
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static String deleteBetweenChar(String wbText, String c1, String c2){
		int a = wbText.indexOf(c1);
		int b = wbText.indexOf(c2, a+1);
		while(a>=0 && b>=0){
			 wbText = wbText.substring(0, a) + wbText.substring(b+1);
			 a = wbText.indexOf(c1);
			 b = wbText.indexOf(c2, a+1);
		}
		return wbText;
	}
	
	/**
	 * 删除空格 回车 制表
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
