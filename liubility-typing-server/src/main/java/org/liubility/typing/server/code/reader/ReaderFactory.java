package org.liubility.typing.server.code.reader;

import java.io.BufferedReader;

/**
 * @Author: JDragon
 * @Data:2022/9/9 22:04
 * @Description:
 */
public interface ReaderFactory {

    /**
     * 获取读取词库的reader
     *
     * @param path 词库路径
     * @return BufferedReader
     */
    BufferedReader getReader(String path);
}
