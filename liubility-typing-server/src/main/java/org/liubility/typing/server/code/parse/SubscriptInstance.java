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

    private int next;//下一跳

    @JsonIgnore
    private Map<Integer, TreeMap<Integer, PreInfo>> preInfoMap;//上一跳

    private String word;

    private String wordCode;

    private String words;

    private String wordsCode;

    private String type;

    @JsonIgnore
    private boolean useSign;

    @JsonIgnore
    private boolean useWordSign;

    @JsonIgnore
    private int codeLengthTemp;

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

    public PreInfo getMinPre(int codeLength) {
        TreeMap<Integer, PreInfo> preInfoTreeMap = preInfoMap.get(codeLength);
        if (preInfoTreeMap == null || preInfoTreeMap.firstEntry() == null) {
            return null;
        }
        return preInfoTreeMap.firstEntry().getValue();
    }

    public SubscriptInstance(int i) {
        next = i;
        preInfoMap = new HashMap<>();
        wordCode = "";
        word = "";
        useSign = false;
        useWordSign = false;
        codeLengthTemp = 0;
    }

    public SubscriptInstance(int i, String word, String wordCode) {
        this(i);
        this.wordCode = wordCode;
        this.word = word;
    }

    public void addPre(int length, int pre, String words, String wordsCode, String type) {
        TreeMap<Integer, PreInfo> map = preInfoMap.get(length);
        if (map == null) {
            map = new TreeMap<>();
            this.preInfoMap.put(length, map);
        }
        map.put(pre, new PreInfo(pre, words, wordsCode, type));
    }

    @JsonIgnore
    public Map<Integer, PreInfo> getShortCodePreInfo() {
        return preInfoMap.get(codeLengthTemp);
    }
}