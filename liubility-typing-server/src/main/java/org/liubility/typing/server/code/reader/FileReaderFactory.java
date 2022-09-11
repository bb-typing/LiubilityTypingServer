package org.liubility.typing.server.code.reader;

import cn.hutool.core.io.resource.ResourceUtil;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;

/**
 * @Author: JDragon
 * @Data:2022/9/9 22:09
 * @Description:
 */
public class FileReaderFactory implements ReaderFactory {
    @Override
    public BufferedReader getReader(String path) {
        return ResourceUtil.getReader(path, StandardCharsets.UTF_8);
    }
}
