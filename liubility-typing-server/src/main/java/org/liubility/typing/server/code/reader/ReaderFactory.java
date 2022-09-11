package org.liubility.typing.server.code.reader;

import java.io.BufferedReader;

/**
 * @Author: JDragon
 * @Data:2022/9/9 22:04
 * @Description:
 */
public interface ReaderFactory {
    BufferedReader getReader(String path);
}
