package org.liubility.typing.server.code.libs;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: JDragon
 * @Data:2022/9/1 15:33
 * @Description: 词硁类
 */
@Slf4j
public class TrieWordLib extends WordLib {

    private final TrieNode root = new TrieNode();

    public TrieWordLib(String wordLibFilePath, String filterDuplicateSymbols, int codeMaxLength, String leader) {
        super(wordLibFilePath, filterDuplicateSymbols, codeMaxLength, leader);
    }

    @Override
    public void dictPut(String word, String code) {
        List<String> wordChars = word.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
        if (wordChars.size() == 0) return;
        TrieNode node = getNode(word);
        if (node == null) {
            node = root;
            for (String wordChar : wordChars) {
                if (node.getChildren() == null) {
                    node.setChildren(new HashMap<>());
                }
                Map<String, TrieNode> children = node.getChildren();
                node = children.computeIfAbsent(wordChar, (e) -> new TrieNode());
            }
            node.setCode(code);
        } else if (node.getCode() == null ||
                node.getCode().replaceAll(getDefaultUpSymbol(), "").length() > code.length()) {
            node.setCode(code);
        }
    }

    @Override
    public String getCode(String word) {
        return Optional.ofNullable(getNode(word)).orElse(new TrieNode()).getCode();
    }

    public TrieNode getNode(String word) {
        List<String> wordChars = word.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());
        TrieNode lastNode = root;
        for (String wordChar : wordChars) {
            Map<String, TrieNode> children = lastNode.getChildren();
            if (children == null) {
                return null;
            }
            lastNode = children.get(wordChar);
            if (lastNode == null) {
                return null;
            }
        }
        return lastNode;
    }

    @Override
    public Map<String, String> dictToMap() {
        return dictToMap(new HashMap<>(), "", root);
    }

    public Map<String, String> dictToMap(Map<String, String> map, String prefix, TrieNode currentNode) {
        Map<String, TrieNode> children = currentNode.getChildren();
        if (children == null) return map;
        for (Map.Entry<String, TrieNode> entry : children.entrySet()) {
            String key = entry.getKey();
            TrieNode node = entry.getValue();
            if (node.getCode() != null) {
                map.put(prefix + key, node.getCode());
            }
            dictToMap(map, prefix + key, node);
        }
        return map;
    }

    @Data
    public static class TrieNode {

        private Map<String, TrieNode> children;

        private String code;

    }
}