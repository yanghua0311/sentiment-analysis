
package com.sentiment.analysis.sentimentanalysis.utils;

import com.sentiment.analysis.sentimentanalysis.controller.SentimentAnalysController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 情感词分词类
 *
 * @author
 */
public class SentimentAnalysis {

    // 负面情感词缓存常量
    private static List<String> passive = new ArrayList<String>();
    // 积极情感词缓存常量
    private static List<String> positive = new ArrayList<String>();
    // 程度情感词
    private static List<String> transitional = new ArrayList<String>();
    private static List<String> degrees = new ArrayList<String>();

    // 静态代码块 加载一次
    static {
        passive = readData("/root/home/dict/passive.dic");
        positive = readData("/root/home/dict/positive.dic");
        degrees = readData("/root/home/dict/degree.dic");
        transitional = readData("/root/home/dict/transitional.dic");
//        passive = readData("dict/passive.dic");
//        positive = readData("dict/positive.dic");
//        degrees = readData("dict/degree.dic");
//        transitional = readData("dict/transitional.dic");


    }

    /**
     * 读取字符串
     *
     * @param filePath 文件相对classpath的路径
     * @return
     */
    private static List<String> readData(String filePath) {
//        URL url = SentimentAnalysController.class.getClassLoader().getResource(filePath);
        File file = new File(filePath);
        BufferedReader reader = null;
        List<String> ss = new ArrayList<String>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String item;
            while ((item = reader.readLine()) != null) {
                item = item.trim();
                if (item != "")
                    ss.add(item);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return ss;
    }

    /**
     * 分析传入的分词积极情感和消极情感的个数
     *
     * @param words
     * @return
     */
    public static List<Integer> analysis(List<String> words) {
        //结果集
        List<Integer> count = new ArrayList<Integer>();
        Integer positiveCount = 0;
        Integer passiveCount = 0;
        //否定词
        List<Integer> transitionalPosition = new ArrayList<Integer>();
        //程度副词
        List<Integer> degree = new ArrayList<Integer>();
        String item;
        List<String> contains = new ArrayList<String>();
        for (int i = 0, len = words.size(); i < len; i++) {
            item = words.get(i);
            // 积极
            if (transitional.contains(item)) {
                transitionalPosition.add(i);
            }
            if (degrees.contains(item)) {
                degree.add(i);
            }
            if (positive.contains(item)) {
                contains.add(item);
                for (Integer a : transitionalPosition) {
                    if ((i - a) > 0 && 2 > (i - a)) {
                        passiveCount++;
                    } else {
                        positiveCount++;
                    }

                }
                for (Integer b : degree) {
                    if ((i - b) > 0 && 2 > (i - b)) {
                        positiveCount++;

                    }
                }
            }
            // 消极
            if (passive.contains(item)) {
                contains.add(item);
                for (Integer a : transitionalPosition) {
                    if ((i - a) > 0 && 2 > (i - a)) {
                        positiveCount++;
                    } else {
                        passiveCount++;
                    }
                }
                for (Integer b : degree) {
                    if ((i - b) > 0 && 2 > (i - b)) {
                        passiveCount++;

                    }
                }
            }
        }
        count.add(positiveCount);
        count.add(passiveCount);
        return count;
    }

}
