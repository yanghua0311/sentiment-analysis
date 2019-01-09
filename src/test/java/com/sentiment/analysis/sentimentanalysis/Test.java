package com.sentiment.analysis.sentimentanalysis;

import com.sentiment.analysis.sentimentanalysis.service.AnalysisService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by youngwa on 2018/12/07. 22:15
 */
public class Test {
    public static void main(String[] args) {
//        AnalysisService analysisService = new AnalysisService();
//        analysisService.execute("我很快乐啊");
        String reg = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(null);
    }
}
