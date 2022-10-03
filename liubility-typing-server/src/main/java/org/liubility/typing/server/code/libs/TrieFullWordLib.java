package org.liubility.typing.server.code.libs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.liubility.typing.server.code.reader.ReaderFactory;

import java.util.*;

/**
 * @Author: JDragon
 * @Data:2022/10/3 12:55
 * @Description:
 */
public class TrieFullWordLib extends TrieWordLib {

    public TrieFullWordLib(ReaderFactory readerFactory, String wordLibFilePath) {
        super(readerFactory, wordLibFilePath, "", 0, "");
    }

    public TrieFullWordLib(ReaderFactory readerFactory, String wordLibFilePath, String filterDuplicateSymbols, int codeMaxLength, String leader) {
        super(readerFactory, wordLibFilePath, filterDuplicateSymbols, codeMaxLength, leader);
    }

    @Override
    public TrieWordLib.TrieNode createNode() {
        return new TrieNode();
    }

    @Override
    public boolean dictPut(String word, String code) {
        List<String> wordChars = splitWord(word);
        if (wordChars.size() == 0) {
            return true;
        }
        TrieWordLib.TrieNode node = getNode(word);
        if (node == null) {
            node = root;
            for (String wordChar : wordChars) {
                if (node.getChildren() == null) {
                    node.setChildren(new HashMap<>());
                }
                Map<String, TrieWordLib.TrieNode> children = node.getChildren();
                node = children.computeIfAbsent(wordChar, (e) -> createNode());
            }
            node.setCode(code);
            return true;
        } else if (node.getCode() == null) {
            node.setCode(code);
            return true;
        } else if (node.getCode().replaceAll(getDefaultUpSymbol(), "").length() > code.replaceAll(getDefaultUpSymbol(), "").length()) {
            ((TrieNode) node).setFirstCode(code);
        } else {
            node.setCode(code);
            return true;
        }
        return false;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TrieNode extends TrieWordLib.TrieNode {

        private Map<String, TrieWordLib.TrieNode> children;

        private List<String> codes;

        @Override
        public String getCode() {
            if (codes == null || codes.isEmpty()) {
                return null;
            }
            return codes.get(0);
        }

        @Override
        public void setCode(String code) {
            if (codes == null) {
                codes = new LinkedList<>();
            }
            int i = 0;
            int length = code.length();
            for (String item : codes) {
                if (length <= item.length()) {
                    break;
                }
                i++;
            }
            codes.add(i, code);
        }

        public void setFirstCode(String code) {
            if (codes == null) {
                codes = new LinkedList<>();
            }
            codes.add(0, code);
        }
    }
}
