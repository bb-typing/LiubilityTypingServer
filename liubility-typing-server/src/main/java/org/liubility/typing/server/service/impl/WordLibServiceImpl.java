package org.liubility.typing.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.commons.util.TimingMap;
import org.liubility.typing.server.code.compare.CompareFeelDeviationWeights;
import org.liubility.typing.server.code.convert.MockTypeConvert;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.code.reader.MinioReaderFactory;
import org.liubility.typing.server.code.reader.ReaderFactory;
import org.liubility.typing.server.domain.dto.WordLibDTO;
import org.liubility.typing.server.domain.entity.WordLibInfo;
import org.liubility.typing.server.mappers.WordLibMapper;
import org.liubility.typing.server.minio.BucketConstant;
import org.liubility.typing.server.minio.Minio;
import org.liubility.typing.server.minio.service.MinioServiceImpl;
import org.liubility.typing.server.minio.service.OssFileInfoVO;
import org.liubility.typing.server.service.WordLibService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Author: JDragon
 * @Data:2022/9/10 1:36
 * @Description:
 */
@Service
@Slf4j
public class WordLibServiceImpl extends ServiceImpl<WordLibMapper, WordLibInfo> implements WordLibService {

    private final Minio minio;

    @Resource
    private TimingMap<Long, TrieWordParser> wordParserTimingMap;

    private final MinioServiceImpl minioService;

    public WordLibServiceImpl(Minio minio, MinioServiceImpl minioService) {
        this.minio = minio;
        this.minioService = minioService;
    }

    @Override
    public void uploadWordLib(Long userId, WordLibDTO wordLibDTO) {
        OssFileInfoVO upload;
        try {
            upload = minioService.upload(wordLibDTO.getFile(), BucketConstant.WORD_LIB_BUCKET);
        } catch (Exception e) {
            log.error("上传词库到对象存储异常", e);
            throw new LBRuntimeException("上传词库到对象存储异常", e);
        }

        String duplicateSymbols = wordLibDTO.getDuplicateSymbols();
        Integer codeMaxLength = wordLibDTO.getCodeMaxLength();
        String leaderSymbols = wordLibDTO.getLeaderSymbols();
        String filePath = upload.getFilePath();
        Double duplicateSymbolWeight = wordLibDTO.getDuplicateSymbolWeight();
        Double wordLengthWeight = wordLibDTO.getWordLengthWeight();
        String keyBoardPartition = wordLibDTO.getKeyBoardPartition();

        ReaderFactory readerFactory = new MinioReaderFactory(minio);

        try {
            TrieWordLib symbol = new TrieWordLib(readerFactory, "symbol.txt", "", 0, "");
            TrieWordLib wordLib = new TrieWordLib(readerFactory, filePath, duplicateSymbols, codeMaxLength, leaderSymbols);
            wordLib.merge(symbol);

            CompareFeelDeviationWeights compareFeelDeviationWeights = new CompareFeelDeviationWeights(duplicateSymbolWeight, wordLengthWeight, wordLib.getFilterDuplicateSymbols());
            compareFeelDeviationWeights.addKeyAllBoardPartition(Arrays.asList(keyBoardPartition.split("\\|")));

            MockTypeConvert mockTypeConvert = new MockTypeConvert(duplicateSymbols, wordLib.getDefaultUpSymbol());

            TrieWordParser trieWordParser = new TrieWordParser(wordLib, symbol, mockTypeConvert, compareFeelDeviationWeights);

            WordLibInfo wordLibInfo = WordLibInfo.builder()
                    .userId(userId)
                    .wordLibPath(filePath)
                    .storageType(1)
                    .wordCount(wordLib.getWordCount())
                    .wordMaxLength(wordLib.getMaxWordLength())
                    .duplicateSymbols(duplicateSymbols)
                    .codeMaxLength(codeMaxLength)
                    .keyBoardPartition(keyBoardPartition)
                    .duplicateSymbolWeight(duplicateSymbolWeight)
                    .wordLengthWeight(wordLengthWeight)
                    .leaderSymbols(leaderSymbols)
                    .build();

            wordLibInfo.insert();
            wordParserTimingMap.put(userId, trieWordParser);
        } catch (Exception e) {
            log.error("插入词库异常", e);
            //回滚文件
            minioService.delete(upload.getBucket(), upload.getFilePath());
            throw new LBRuntimeException("插入词库异常", e);
        }
    }
}
