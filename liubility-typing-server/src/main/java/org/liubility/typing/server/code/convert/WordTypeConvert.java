package org.liubility.typing.server.code.convert;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: JDragon
 * @Data:2022/9/3 0:40
 * @Description:
 */
public abstract class WordTypeConvert {

    /**
     * 选重符号
     */
    @Getter
    protected final String filterDuplicateSymbols;

    @Getter
    protected final String defaultUpSymbol;

    protected WordTypeConvert(String filterDuplicateSymbols, String defaultUpSymbol) {
        this.filterDuplicateSymbols = filterDuplicateSymbols;
        this.defaultUpSymbol = defaultUpSymbol;
    }

    public abstract String convert(String code);

}
