package com.sentiment.analysis.sentimentanalysis.enums;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by youngwa on 2018/12/07. 20:19
 */
public enum EmojiEnum {
    //积极：positive
    HAPPY("\uD83D\uDE0A", 1, 0.9),
    LAUGH("\uD83D\uDE04", 1, 0.8),
    SMILECRY("\uD83D\uDE02", 1, 0.9),
    //消极：passive
    GLOOMY("\uD83D\uDE14️", 2, 0.9),
    SAD("\uD83D\uDE2D", 2, 0.9),
    ANGER("\uD83D\uDE21", 2, 0.9),
    //中性：neutral
    DOG("\uD83D\uDC36", 3, 0.5),
    DAZE("\uD83D\uDE42", 3, 0.5),
    CLOWN("\uD83E\uDD21", 3, 0.5);

    public String name;
    public double score;
    public int type; // 1:积极，2：消极，3：中性


    EmojiEnum(String name, int type, double score) {
        this.name = name;
        this.type = type;
        this.score = score;
    }

    //获取权重误差小于正负0.2的top3表情
    public static List<String> getEmojis(int type, double score) {
        //中性时返回随机3个中性表情
        if (type == 3) {
            List<String> neutralEmojis = new ArrayList<>();
            for (EmojiEnum emoji : values()) {
                if (emoji.type == 3) {
                    neutralEmojis.add(emoji.name);
                }
            }
            if (neutralEmojis.size() < 4) {
                return neutralEmojis;
            }
            List<String> randomResult = new ArrayList<>();
            while (randomResult.size() < 4) {
                int random = new Random().nextInt(neutralEmojis.size() - 1);
                if (!randomResult.contains(neutralEmojis.get(random))) {
                    randomResult.add(neutralEmojis.get(random));
                }
            }
            return randomResult;
        }
        //取消极或者积极的表情
        Map<String, Double> emojisMap = new HashMap<>();
        double minScore = 1.0;
        String minName = null;
        for (EmojiEnum emoji : values()) {
            if (emoji.type == type) {
                if ((absoluteValue(emoji.score, score)) < 0.2) {
                    if (emojisMap.size() == 3 && minScore < emoji.score) {
                        emojisMap.remove(minName);
                        emojisMap.put(emoji.name, emoji.score);
                        minScore = emoji.score;
                        minName = emoji.name;
                        for (Map.Entry<String, Double> map : emojisMap.entrySet()) {
                            if (minScore > map.getValue()) {
                                minScore = map.getValue();
                                minName = map.getKey();
                            }
                        }
                        continue;
                    }
                    emojisMap.put(emoji.name, emoji.score);
                    if (minScore > emoji.score) {
                        minScore = emoji.score;
                        minName = emoji.name;
                    }
                }
            }
        }
        return emojisMap.keySet().stream().collect(Collectors.toList());
    }

    public static double absoluteValue(double a, double b) {
        if (a > b) {
            return a - b;
        } else {
            return b - a;
        }
    }
}
