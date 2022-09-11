package org.liubility.typing.server.code.libs;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.liubility.typing.server.code.reader.ReaderFactory;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @Author: JDragon
 * @Data:2022/9/1 17:45
 * @Description:
 */
@Slf4j
public class WordLib {

    /**
     * 词库文件路径
     */
    @Getter
    private final String wordLibFilePath;


    /**
     * 最长编码 - n码顶屏
     */
    @Getter
    private final int codeMaxLength;


    /**
     * 引导符 编码以引导符开头，不需要空格上屏
     */
    @Getter
    private final List<String> leader;

    /**
     * 选重符号
     */
    @Getter
    protected final List<String> filterDuplicateSymbols;

    /**
     * 词库字典
     * key: 词语长度
     * value: 字典
     */
    @Getter
    private final Map<Integer, Map<String, String>> wordCodeDictMap = new TreeMap<>();//词组码表

    protected final Map<String, Integer> codeWordDict = new HashMap<>();

    private final ReaderFactory readerFactory;

    public WordLib(ReaderFactory readerFactory, String wordLibFilePath, String filterDuplicateSymbols, int codeMaxLength, String leader) {
        this.readerFactory = readerFactory;
        this.wordLibFilePath = wordLibFilePath;
        this.codeMaxLength = codeMaxLength;
        this.leader = leader.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
        this.filterDuplicateSymbols = filterDuplicateSymbols.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
        this.init();
    }

    /**
     * 修改后不需要生成码表
     * 提供按照词库重复编码的顺序来计算选重符号
     */
    public void init() {
        try (BufferedReader reader = readerFactory.getReader(getWordLibFilePath())) {
            String temp;
            while ((temp = reader.readLine()) != null) {
                if (temp.startsWith("---")) continue;
                if (temp.contains("#")) {
                    temp = temp.substring(0, temp.indexOf("#"));
                }
                String[] spliced = temp.split("\\s+");
                if (spliced.length == 1) {
                    spliced = new String[]{spliced[0], "? "};
                }
                if (spliced.length != 2) continue;
                String word = spliced[0];
                String code = spliced[1];
                code = checkDuplicate(code);
                dictPut(word, code);
            }
        } catch (Exception e) {
            log.error("读取词库文件失败", e);
        }
    }

    public void dictPutAll(Map<String, String> dict) {
        for (Map.Entry<String, String> entry : dict.entrySet()) {
            dictPut(entry.getKey(), entry.getValue());
        }
    }

    public void dictPut(String word, String code) {
        //计算编码选重符号
        Map<String, String> wordCodeDict = getWordCodeDictMap().computeIfAbsent(word.length(), (e) -> new HashMap<>());
        String codeFromDict = wordCodeDict.get(word);
        if (codeFromDict == null || codeFromDict.replaceAll(getDefaultUpSymbol(), "").length() > code.length()) {
            wordCodeDict.put(word, code);
        }
    }

    /**
     * 合并目标字典
     */
    public void merge(WordLib wordLib) {
        dictPutAll(wordLib.dictToMap());
    }

    /**
     * 将字典转换为map形式。
     */
    public Map<String, String> dictToMap() {
        Map<String, String> map = new HashMap<>();
        for (Map<String, String> stringStringMap : wordCodeDictMap.values()) {
            map.putAll(stringStringMap);
        }
        return map;
    }

    /**
     * <p>编码解析为带有上屏键编码</p>
     * <p>结果受到 上屏码长，引导符，选重符号 影响</p>
     * 举例：
     * <p>a -> a_</p>
     * <p>a -> a2</p>
     * <p>;a -> ！</p>
     */
    public String checkDuplicate(String code) {
        //计算编码选重符号
        String codeDuplicate = code;
        if (getCodeMaxLength() != getNoMaxLengthSign() && code.length() < getCodeMaxLength() && !leader.contains(code.substring(0, 1))) {
            codeDuplicate = code + getDefaultUpSymbol();
        }
        Integer duplicateNum = codeWordDict.get(code);
        if (duplicateNum == null) {
            codeWordDict.put(code, 0);
        } else {
            if (filterDuplicateSymbols.size() > duplicateNum) {
                codeDuplicate = code + filterDuplicateSymbols.get(duplicateNum);
            } else {
                codeDuplicate = code + "?";
            }
            codeWordDict.put(code, ++duplicateNum);
        }
        return codeDuplicate;
    }


    public String getCode(String word) {
        Map<String, String> dict = getWordCodeDictMap().get(word.length());
        if (dict != null) {
            return dict.get(word);
        }
        return null;
    }


    public String getDefaultUpSymbol() {
        return "_";
    }

    public int getNoMaxLengthSign() {
        return 0;
    }
}
