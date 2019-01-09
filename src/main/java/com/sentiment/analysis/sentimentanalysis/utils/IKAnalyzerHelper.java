package com.sentiment.analysis.sentimentanalysis.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 微博内容中文分词辅助类
 * @author
 *
 */
public class IKAnalyzerHelper {
	
	/**
	 * 执行分词
	 * @param s
	 * @return
	 */
	public static List<String> analyzer(String s){
		List<String> res = new ArrayList<String>();
		// 创建分词器
		Analyzer anal = new IKAnalyzer(true);
		StringReader reader = new StringReader(s);
		TokenStream ts = null;
		try {
			// 执行分词
			ts = anal.tokenStream("", reader);
			CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);

			// 遍历分词
			while(ts.incrementToken()){
				res.add(term.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ts.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
