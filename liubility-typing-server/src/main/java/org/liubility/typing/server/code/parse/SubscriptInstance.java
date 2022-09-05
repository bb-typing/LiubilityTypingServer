package org.liubility.typing.server.code.parse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.*;

/**
 * @Author: JDragon
 * @Data:2022/9/2 15:09
 * @Description:
 */
@Data
public class SubscriptInstance {

    private String word;

    private String wordCode;

    private String words;

    private String wordsCode;

    private String type;

    private int next;//下一跳

    /**
     * <权重值(编码长度等),<上一跳下标,上一跳信息>>
     */
    @JsonIgnore
    private TreeMap<Double, TreeMap<Integer, PreInfo>> preInfoMap;//上一跳

    @JsonIgnore
    private boolean useSign;

    @JsonIgnore
    private boolean useWordSign;

    @JsonIgnore
    private int codeLengthTemp;

    /**
     * 左右击键差值
     */
    @JsonIgnore
    private double feelDeviation;


    @JsonIgnore
    private double weights;

    @Data
    public static class PreInfo {
        //同长度不同上跳表，用于动态词提
        private Integer pre;
        private String type;
        private String wordsCode;//最短编码路径的编码
        private String words;//最短编码路径的词条

        PreInfo(int pre, String words, String wordsCode, String type) {
            this.pre = pre;
            this.type = type;
            this.words = words;
            this.wordsCode = wordsCode;
        }
    }

    public PreInfo getMinPre() {
        TreeMap<Integer, PreInfo> preInfoTreeMap = preInfoMap.get(weights);
        if (preInfoTreeMap == null || preInfoTreeMap.firstEntry() == null) {
            return null;
        }
        return preInfoTreeMap.firstEntry().getValue();
    }

    public SubscriptInstance(int i) {
        next = i;
        preInfoMap = new TreeMap<>();
        wordCode = "";
        word = "";
        useSign = false;
        useWordSign = false;
        codeLengthTemp = 0;
        feelDeviation = 0;
        weights = 0;
        type = "0";
    }

    public SubscriptInstance(int i, String word, String wordCode) {
        this(i);
        this.wordCode = wordCode;
        this.word = word;
        this.words = word;
        this.wordsCode = wordCode;
    }

    public void addPre(double length, int pre, String words, String wordsCode, String type) {
        TreeMap<Integer, PreInfo> map = preInfoMap.get(length);
        if (map == null) {
            map = new TreeMap<>();
            this.preInfoMap.put(length, map);
        }
        map.put(pre, new PreInfo(pre, words, wordsCode, type));
    }
}