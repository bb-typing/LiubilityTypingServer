package org.liubility.typing.server.code.libs;

import lombok.extern.slf4j.Slf4j;
import org.liubility.typing.server.code.reader.ReaderFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: JDragon
 * @Data:2022/9/19 22:01
 * @Description: 英文速录型词库  大小写不敏感，以空格分词
 */

@Slf4j
public class EnglishTrieWordLib extends TrieWordLib {
    public EnglishTrieWordLib(ReaderFactory readerFactory, String wordLibFilePath) {
        super(readerFactory, wordLibFilePath, "", 0, "");
    }

//    @Override
//    public TrieNode getNode(String word) {
//        return super.getNode(word);
//    }
//
//    @Override
//    public String getCode(String word) {
//        return super.getCode(word);
//    }
//
//    @Override
//    public List<String> splitWord(String word) {
//        return Arrays.stream(word.split("\\s+")).collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean dictPut(String word, String code) {
//        return super.dictPut(word, code.toUpperCase().trim());
//    }
//
//    @Override
//    public Map<String, String> dictToMap(Map<String, String> map, String prefix, TrieNode currentNode) {
//        Map<String, TrieNode> children = currentNode.getChildren();
//        if (children == null) return map;
//        for (Map.Entry<String, TrieNode> entry : children.entrySet()) {
//            String key = entry.getKey();
//            TrieNode node = entry.getValue();
//            if (node.getCode() != null) {
//                map.put(prefix + " " + key, node.getCode());
//            }
//            dictToMap(map, prefix + " " + key, node);
//        }
//        return map;
//    }

    @Override
    public ProcessLineResult processLine(String wordLibLine) {
        String[] spliced = wordLibLine.split("\\t");
        if (spliced.length == 1) {
            //英文速录未知词算一码
            spliced = new String[]{spliced[0], "?"};
        } else if (spliced.length != 2) {
            return new ProcessLineResult(false);
        }
        return new ProcessLineResult(true, spliced);
    }
}
