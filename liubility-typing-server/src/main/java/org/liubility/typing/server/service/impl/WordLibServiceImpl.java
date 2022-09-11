package org.liubility.typing.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.commons.util.TimingMap;
import org.liubility.typing.server.code.compare.CompareFeelDeviationWeights;
import org.liubility.typing.server.code.convert.MockTypeConvert;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.SubscriptInstance;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.code.reader.MinioReaderFactory;
import org.liubility.typing.server.code.reader.ReaderFactory;
import org.liubility.typing.server.domain.dto.WordLibDTO;
import org.liubility.typing.server.domain.entity.WordLibInfo;
import org.liubility.typing.server.domain.vo.TypingTips;
import org.liubility.typing.server.exception.WordLibCode;
import org.liubility.typing.server.mappers.WordLibMapper;
import org.liubility.typing.server.minio.BucketConstant;
import org.liubility.typing.server.minio.Minio;
import org.liubility.typing.server.minio.service.MinioServiceImpl;
import org.liubility.typing.server.minio.service.OssFileInfoVO;
import org.liubility.typing.server.service.WordLibService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

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
    public TrieWordParser uploadWordLib(Long userId, WordLibDTO wordLibDTO) {
        OssFileInfoVO upload;
        try {
            upload = minioService.upload(wordLibDTO.getFile(), BucketConstant.WORD_LIB_BUCKET);
        } catch (Exception e) {
            log.error("上传词库到对象存储异常", e);
            throw new LBRuntimeException(WordLibCode.UPLOAD_LIB_ERROR, e);
        }
        WordLibInfo wordLibInfo = WordLibInfo.builder()
                .userId(userId)
                .wordLibPath(upload.getFilePath())
                .storageType(1)
                .duplicateSymbols(wordLibDTO.getDuplicateSymbols())
                .codeMaxLength(wordLibDTO.getCodeMaxLength())
                .keyBoardPartition(wordLibDTO.getKeyBoardPartition())
                .duplicateSymbolWeight(wordLibDTO.getDuplicateSymbolWeight())
                .wordLengthWeight(wordLibDTO.getWordLengthWeight())
                .leaderSymbols(wordLibDTO.getLeaderSymbols())
                .build();
        try {
            wordLibInfo.insert();
        } catch (Exception e) {
            log.error("插入词库异常", e);
            //回滚
            minioService.delete(upload.getBucket(), upload.getFilePath());
            throw new LBRuntimeException(WordLibCode.INSERT_ERROR, e);
        }
        try {
            return loadWordLib(wordLibInfo);
        } catch (Exception e) {
            log.error("加载词库异常", e);
            //回滚
            minioService.delete(upload.getBucket(), upload.getFilePath());
            wordLibInfo.deleteById();
            throw new LBRuntimeException(WordLibCode.LOAD_LIB_ERROR, e);
        }
    }


    @Override
    public TrieWordParser loadWordLib(Long userId) {
        WordLibInfo cond = WordLibInfo.builder().userId(userId).priority(1).build();
        WordLibInfo wordLibInfo = selectOneByEntity(cond);
        return loadWordLib(wordLibInfo);
    }

    @Override
    public TypingTips typingTips(Long userId, String article) {
        TrieWordParser trieWordParser = wordParserTimingMap.get(userId);
        if (trieWordParser == null) {
            trieWordParser = loadWordLib(userId);
        }
        SubscriptInstance[] subscriptInstances = trieWordParser.parse(article);
        String codes = trieWordParser.printCode(subscriptInstances);
        return new TypingTips(subscriptInstances, codes);
    }

    public WordLibInfo selectOneByEntity(WordLibInfo wordLibInfo) {
        LambdaQueryWrapper<WordLibInfo> wordLibInfoLambdaQueryWrapper = new LambdaQueryWrapper<>(wordLibInfo);
        return getOne(wordLibInfoLambdaQueryWrapper);
    }

    public List<WordLibInfo> selectByEntity(WordLibInfo wordLibInfo) {
        LambdaQueryWrapper<WordLibInfo> wordLibInfoLambdaQueryWrapper = new LambdaQueryWrapper<>(wordLibInfo);
        return list(wordLibInfoLambdaQueryWrapper);
    }

    public TrieWordParser loadWordLib(WordLibInfo wordLibInfo) {
        String duplicateSymbols = wordLibInfo.getDuplicateSymbols();
        ReaderFactory readerFactory = new MinioReaderFactory(minio);
        TrieWordLib symbol = new TrieWordLib(readerFactory, "symbol.txt");
        TrieWordLib wordLib = new TrieWordLib(readerFactory, wordLibInfo.getWordLibPath(), duplicateSymbols, wordLibInfo.getCodeMaxLength(), wordLibInfo.getLeaderSymbols());

        wordLibInfo.setWordCount(wordLib.getWordCount());
        wordLibInfo.setWordMaxLength(wordLibInfo.getWordMaxLength());

        wordLib.merge(symbol);

        CompareFeelDeviationWeights compareFeelDeviationWeights = new CompareFeelDeviationWeights(wordLibInfo.getDuplicateSymbolWeight(), wordLibInfo.getWordLengthWeight(), wordLib.getFilterDuplicateSymbols());
        compareFeelDeviationWeights.addKeyAllBoardPartition(Arrays.asList(wordLibInfo.getKeyBoardPartition().split("\\|")));

        MockTypeConvert mockTypeConvert = new MockTypeConvert(duplicateSymbols, wordLib.getDefaultUpSymbol());

        TrieWordParser trieWordParser = new TrieWordParser(wordLib, symbol, mockTypeConvert, compareFeelDeviationWeights);
        wordParserTimingMap.put(wordLibInfo.getUserId(), trieWordParser);
        return trieWordParser;
    }
}
