package org.liubility.typing.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.commons.util.TimingMap;
import org.liubility.typing.server.code.compare.CompareFeelDeviationWeights;
import org.liubility.typing.server.code.convert.MockTypeConvert;
import org.liubility.typing.server.code.libs.TrieWordLib;
import org.liubility.typing.server.code.parse.SubscriptInstance;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.code.reader.ReaderFactory;
import org.liubility.typing.server.config.CodeConfig;
import org.liubility.typing.server.domain.dto.WordLibDTO;
import org.liubility.typing.server.domain.entity.UserWordLibSetting;
import org.liubility.typing.server.domain.entity.WordLibInfo;
import org.liubility.typing.server.domain.vo.TypingTips;
import org.liubility.typing.server.domain.vo.WordLibListPageVO;
import org.liubility.typing.server.enums.exception.WordLibCode;
import org.liubility.typing.server.mappers.WordLibMapper;
import org.liubility.typing.server.minio.BucketConstant;
import org.liubility.typing.server.minio.service.MinioServiceImpl;
import org.liubility.typing.server.minio.service.OssFileInfoVO;
import org.liubility.typing.server.service.UserWordLibSettingService;
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

    @Resource
    private TimingMap<Long, CodeConfig.CacheTrieWordLib> wordLibCache;

    @Resource
    private ReaderFactory minioReaderFactory;

    private final MinioServiceImpl minioService;

    private final UserWordLibSettingService userWordLibSettingService;

    public WordLibServiceImpl(MinioServiceImpl minioService, UserWordLibSettingService userWordLibSettingService) {
        this.minioService = minioService;
        this.userWordLibSettingService = userWordLibSettingService;
    }

    @Override
    public void uploadWordLib(Long userId, WordLibDTO wordLibDTO) {
        OssFileInfoVO upload;
        try {
            upload = minioService.upload(wordLibDTO.getFile(), BucketConstant.WORD_LIB_BUCKET);
        } catch (Exception e) {
            log.error("上传词库到对象存储异常", e);
            throw new LBRuntimeException(WordLibCode.UPLOAD_LIB_ERROR, e);
        }
        WordLibInfo wordLibInfo = WordLibInfo.builder()
                .userId(userId)
                .wordLibName(wordLibDTO.getWordLibName())
                .wordLibPath(upload.getFilePath())
                .storageType(1)
                .codeMaxLength(wordLibDTO.getCodeMaxLength())
                .leaderSymbols(wordLibDTO.getLeaderSymbols())
                .duplicateSymbols(wordLibDTO.getDuplicateSymbols())
                .build();
        try {
            loadWordLib(wordLibInfo);
        } catch (Exception e) {
            log.error("加载词库异常", e);
            //回滚
            minioService.delete(upload.getBucket(), upload.getFilePath());
            throw new LBRuntimeException(WordLibCode.LOAD_LIB_ERROR, e);
        }
        try {
            wordLibInfo.insert();
        } catch (Exception e) {
            log.error("插入词库异常", e);
            //回滚
            minioService.delete(upload.getBucket(), upload.getFilePath());
            throw new LBRuntimeException(WordLibCode.INSERT_ERROR, e);
        }
    }

    @Override
    public TrieWordParser loadParser(Long userId) {
        UserWordLibSetting userDefaultUserSetting = userWordLibSettingService.getUserDefaultUserSetting(userId);
        TrieWordParser trieWordParser = userWordLibSettingService.getCache(userDefaultUserSetting.getId());
        if (trieWordParser == null) {
            WordLibInfo wordLibInfo = getById(userDefaultUserSetting.getWordLibId());
            return loadParser(wordLibInfo, userDefaultUserSetting);
        } else {
            return trieWordParser;
        }
    }

    @Override
    public TypingTips typingTips(Long userId, String article) {
        TrieWordParser trieWordParser = loadParser(userId);
        SubscriptInstance[] subscriptInstances = trieWordParser.parse(article);
        String codes = trieWordParser.printCode(subscriptInstances);
        return new TypingTips(subscriptInstances, codes);
    }


    @Override
    public IPage<WordLibListPageVO> getPageByUserId(IPage<WordLibListPageVO> iPage, Long userId) {
        return baseMapper.getPageByUserId(iPage, userId);
    }

    public WordLibInfo selectOneByEntity(WordLibInfo wordLibInfo) {
        LambdaQueryWrapper<WordLibInfo> wordLibInfoLambdaQueryWrapper = new LambdaQueryWrapper<>(wordLibInfo);
        return getOne(wordLibInfoLambdaQueryWrapper);
    }

    public List<WordLibInfo> selectByEntity(WordLibInfo wordLibInfo) {
        LambdaQueryWrapper<WordLibInfo> wordLibInfoLambdaQueryWrapper = new LambdaQueryWrapper<>(wordLibInfo);
        return list(wordLibInfoLambdaQueryWrapper);
    }

    public WordLibInfo getUserDefaultWordLib(Long userId) {
        UserWordLibSetting userDefaultUserSetting = userWordLibSettingService.getUserDefaultUserSetting(userId);
        WordLibInfo wordLibInfo = getById(userDefaultUserSetting.getWordLibId());
        if (wordLibInfo == null) {
            throw new LBRuntimeException(WordLibCode.NOT_FOUNT_DEFAULT_WORD_LIB);
        }
        return wordLibInfo;
    }


    private TrieWordLib loadWordLib(WordLibInfo wordLibInfo) {
        CodeConfig.CacheTrieWordLib wordLib;
        CodeConfig.CacheTrieWordLib symbol;
        symbol = wordLibCache.get(0L);
        wordLib = wordLibCache.get(wordLibInfo.getId());
        if (symbol == null) {
            log.info("symbol word lib 正在加载");
            symbol = new CodeConfig.CacheTrieWordLib(0L, minioReaderFactory, "symbol.txt");
            wordLibCache.put(0L, symbol);
            log.info("symbol word lib 加载完成");
        }
        if (wordLib == null) {
            log.info("加载词库{}信息：{}", wordLibInfo.getWordLibName(), wordLibInfo);
            wordLib = new CodeConfig.CacheTrieWordLib(wordLibInfo.getId(), minioReaderFactory, wordLibInfo.getWordLibPath(), wordLibInfo.getDuplicateSymbols(), wordLibInfo.getCodeMaxLength(), wordLibInfo.getLeaderSymbols());
            wordLibCache.put(wordLibInfo.getId(), wordLib);
            log.info("加载词库{}信息完成", wordLibInfo.getWordLibName());
        }

        wordLibInfo.setWordCount(wordLib.getWordCount());
        wordLibInfo.setWordMaxLength(wordLib.getMaxWordLength());

        wordLib.merge(symbol);
        return wordLib;
    }

    private TrieWordParser loadParser(WordLibInfo wordLibInfo, UserWordLibSetting userWordLibSetting) {
        TrieWordParser trieWordParser = userWordLibSettingService.getCache(userWordLibSetting.getId());
        if (trieWordParser != null) {
            return trieWordParser;
        }
        TrieWordLib wordLib = loadWordLib(wordLibInfo);
        CodeConfig.CacheTrieWordLib symbol = wordLibCache.get(0L);

        log.info("加载parser：{}", userWordLibSetting);
        CompareFeelDeviationWeights compareFeelDeviationWeights = new CompareFeelDeviationWeights(userWordLibSetting.getDuplicateSymbolWeight(), userWordLibSetting.getWordLengthWeight(), wordLib.getFilterDuplicateSymbols());
        compareFeelDeviationWeights.addKeyAllBoardPartition(Arrays.asList(userWordLibSetting.getKeyBoardPartition().split("\\|")));

        MockTypeConvert mockTypeConvert = new MockTypeConvert(wordLibInfo.getDuplicateSymbols(), wordLib.getDefaultUpSymbol());

        trieWordParser = new TrieWordParser(wordLib, symbol, mockTypeConvert, compareFeelDeviationWeights);
        log.info("加载parser：{}成功", userWordLibSetting.getId());
        userWordLibSettingService.cache(wordLibInfo.getId(), trieWordParser);
        return trieWordParser;
    }
}
