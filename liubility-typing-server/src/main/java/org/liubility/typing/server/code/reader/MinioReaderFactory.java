package org.liubility.typing.server.code.reader;

import cn.hutool.core.io.IoUtil;
import org.liubility.typing.server.minio.Minio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Author: JDragon
 * @Data:2022/9/9 22:04
 * @Description:
 */

public class MinioReaderFactory implements ReaderFactory {

    private final Minio minio;

    public MinioReaderFactory(Minio minio) {
        this.minio = minio;
    }

    @Override
    public BufferedReader getReader(String path) {
        try {
            InputStream inputStream = minio.getObject("word-lib", path);
            return IoUtil.getReader(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
