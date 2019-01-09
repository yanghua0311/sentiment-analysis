package com.sentiment.analysis.sentimentanalysis.service;

import com.sentiment.analysis.sentimentanalysis.enums.EmojiEnum;
import com.sentiment.analysis.sentimentanalysis.utils.IKAnalyzerHelper;
import com.sentiment.analysis.sentimentanalysis.utils.SentimentAnalysis;
import com.sentiment.analysis.sentimentanalysis.utils.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by youngwa on 2018/12/07. 20:49
 */
@Service
public class AnalysisService extends AbstractAnalysisAction {
    @Override
    public boolean verify(String content) {
        if (StringUtils.isEmpty(content)) {
            return false;
        }
        int count = 0;
        String reg = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(content);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        if (count < 4) {
            return false;
        }
        return true;
    }

    @Override
    public String doFilter(String content) {
        String[] deleteChars = {"?", ">>", "<<","-","http://t.cn/"};
        String regex="(http://)(\\p{Lower}+)|(\\.(\\p{Lower}+)+)|(/(\\w+)+)";
        String newWbText = "";
        newWbText = StringHelper.deleteBetweenChar(content, "#", "#"); // 主题
        newWbText = StringHelper.deleteBetweenChar(newWbText, "[", "]"); // 表情
        newWbText = StringHelper.deleteBetweenChar(newWbText, "@", " "); // 提到
        newWbText = StringHelper.deleteBetweenChar(newWbText, "分享图片", "?"); // 表情
        newWbText=newWbText.replaceAll(regex, "");
        // 删除特殊字符
        for(int i=0, len=deleteChars.length; i<len; i++){
            newWbText = newWbText.replace(deleteChars[i], "");
        }
        // 最后执行该项
        newWbText = StringHelper.replaceBlank(newWbText); // 空格 回车 制表符

        return newWbText;
    }

    @Override
    public List<String> action(String content) {
        List<String> words = IKAnalyzerHelper.analyzer(content);
        List<Integer> sentiment = SentimentAnalysis.analysis(words);
        Integer positive = sentiment.get(0);
        Integer passive = sentiment.get(1);
        Integer count = positive + passive;
        // 计算权值
        List<String> emojis = null;
        if (positive > passive) {
            //积极情况 type ：1
            double score = (positive / count) - (passive / count);
            emojis = EmojiEnum.getEmojis(1, score);
        } else if (positive < passive) {
            //消极情况 type ：2
            double score = (passive / count) - (positive / count);
            emojis = EmojiEnum.getEmojis(2, score);
        } else {
            //中性情况 type ：3
            emojis = EmojiEnum.getEmojis(3, 0);
        }
        return emojis;
    }
}