package org.liubility.typing.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/10/3 23:23
 * @Description:
 */
@Data
public class TypedWordsDTO {

    private Long historyId;

    private List<Words> typeWords;

    private String typeCodes;

    @Data
    @AllArgsConstructor
    public static class Words {

        private String wordTips;

        private String codeTips;

        private List<TypeChar> wordsChar;

        private List<TypeChar> codesChar;

        private Long typingTime;

        public String mergeWords() {
            return mergeTypeChars(wordsChar);
        }

        public String mergeCodes() {
            return mergeTypeChars(codesChar);
        }

        private String mergeTypeChars(List<TypeChar> typeChars) {
            if (typeChars == null || typeChars.isEmpty()) {
                return "";
            }
            StringBuilder stringBuilder = new StringBuilder();
            typeChars.stream().filter(typeChar -> typeChar.getDeleteTime() == null)
                    .map(TypeChar::getCharacter)
                    .forEach(stringBuilder::append);
            return stringBuilder.toString();
        }
    }


    @Data
    @AllArgsConstructor
    public static class TypeChar {

        private Character character;

        private Long typingTime;

        private Long deleteTime;

        private Boolean mistake;
    }
}
