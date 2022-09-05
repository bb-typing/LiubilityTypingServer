package org.liubility.typing.server.code.convert;

/**
 * @Author: JDragon
 * @Data:2022/9/3 1:24
 * @Description:
 */
public class MockTypeConvert extends WordTypeConvert {

    public MockTypeConvert(String filterDuplicateSymbols, String defaultUpSymbol) {
        super(filterDuplicateSymbols, defaultUpSymbol);
    }

    @Override
    public String convert(String code) {
        int length = code.length();
        String substring = code.substring(length - 1, length);
        int i = getFilterDuplicateSymbols().indexOf(substring);
        if (getDefaultUpSymbol().equals(substring)) {
            length -= 1;
        }
        i += 2;
        return String.valueOf(length) + i;
    }
}
