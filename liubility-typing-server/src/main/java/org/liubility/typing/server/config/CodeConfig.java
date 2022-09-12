package org.liubility.typing.server.config;

import lombok.Setter;
import org.liubility.commons.util.TimingMap;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.code.reader.MinioReaderFactory;
import org.liubility.typing.server.code.reader.ReaderFactory;
import org.liubility.typing.server.minio.Minio;
import org.liubility.typing.server.minio.MinioProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: JDragon
 * @Data:2022/9/11 20:33
 * @Description:
 */

@Configuration
public class CodeConfig {
    @Bean
    public TimingMap<Long, TrieWordParser> wordParserCache() {
        return new TimingMap<>();
    }

    @Bean
    public TimingMap<Long, CacheTrieWordLib> wordLibCache() {
        TimingMap<Long, CacheTrieWordLib> cacheTrieWordLibTimingMap = new TimingMap<>();
        CacheTrieWordLib.setWordLibCache(cacheTrieWordLibTimingMap);
        return cacheTrieWordLibTimingMap;
    }

    @Bean
    public Minio minio(MinioProperties minioProperties) {
        Minio minio = Minio.builder()
                .endpoint("http://" + minioProperties.getHost() + ":" + minioProperties.getPort())
                .accessKey(minioProperties.getAccessKey())
                .secretKey(minioProperties.getSecretKey())
                .build();
        minio.init();
        return minio;
    }

    @Bean
    public ReaderFactory minioReaderFactory(Minio minio) {
        return new MinioReaderFactory(minio);
    }


    public static class CacheTrieWordLib extends TrieWordLib {

        private final Long id;

        @Setter
        private static TimingMap<Long, CacheTrieWordLib> wordLibCache;

        public CacheTrieWordLib(Long id, ReaderFactory readerFactory, String wordLibFilePath) {
            super(readerFactory, wordLibFilePath);
            this.id = id;
        }

        public CacheTrieWordLib(Long id, ReaderFactory readerFactory, String wordLibFilePath, String filterDuplicateSymbols, int codeMaxLength, String leader) {
            super(readerFactory, wordLibFilePath, filterDuplicateSymbols, codeMaxLength, leader);
            this.id = id;
        }

        @Override
        public String getCode(String word) {
            wordLibCache.get(id);
            return super.getCode(word);
        }

        @Override
        public TrieNode getNode(String word) {
            wordLibCache.get(id);
            return super.getNode(word);
        }
    }
}
