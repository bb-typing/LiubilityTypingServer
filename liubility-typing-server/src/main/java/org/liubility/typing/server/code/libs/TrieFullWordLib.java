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
            Iterator<String> iterator = codes.iterator();
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (length >= item.length()) {
                    break;
                }
                if (iterator.hasNext()) {
                    i++;
                }
            }
            codes.add(i, code);
        }
    }
}
