package org.liubility.typing.server.handler;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.commons.util.FileUtils;
import org.liubility.typing.server.domain.dto.TypeChar;
import org.liubility.typing.server.domain.dto.TypedWordsDTO;
import org.liubility.typing.server.domain.dto.Words;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.typing.server.enums.TypingWordsTypeEnum;
import org.liubility.typing.server.enums.exception.Code203History;
import org.liubility.typing.server.mapstruct.TypeWordsMapStruct;
import org.liubility.typing.server.minio.service.MinioServiceImpl;
import org.liubility.typing.server.mongo.TypeWordsRepository;
import org.liubility.typing.server.mongo.entity.TypeStatusCountMongo;
import org.liubility.typing.server.mongo.TypeStatusCountRepository;
import org.liubility.typing.server.mongo.entity.TypedWordsMongo;
import org.liubility.typing.server.service.TypingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: JDragon
 * @Data:2022/10/6 14:25
 * @Description: 打错词应该包含：词组错误，词组删除，词组拆分后错误，词组延伸后错误，编码删除。
 * 拆词应该包含：词组拆分正确
 * 漏词应该包含：词组延伸
 * 其他分类不确定：词组未错误删除
 * <p>
 * <p>
 * 词组状态：正确，错误
 * 词组删改状态：true,false
 * 词组对比状态：全等，拆分，延伸
 * 编码删改状态：true,false
 * 编码对比状态：全等，更简，更长
 */
@Service
public class TypingWordsDetailHandler {

    private final TypingHistoryService typingHistoryService;

    private final TypeWordsRepository typeWordsRepository;

    private final TypeStatusCountRepository typeStatusCountRepository;

    private final TypeWordsMapStruct typeWordsMapStruct;

    public TypingWordsDetailHandler(TypingHistoryService typingHistoryService, TypeWordsRepository typeWordsRepository, TypeStatusCountRepository typeStatusCountRepository, TypeWordsMapStruct typeWordsMapStruct) {
        this.typingHistoryService = typingHistoryService;
        this.typeWordsRepository = typeWordsRepository;
        this.typeStatusCountRepository = typeStatusCountRepository;
        this.typeWordsMapStruct = typeWordsMapStruct;
    }

    public void saveTypingWordsDetail(TypedWordsDTO typedWordsDTO, Long userId) {
        Long historyId = typedWordsDTO.getHistoryId();
        TypingHistory history = typingHistoryService.getById(historyId);
        if (history == null) {
            return;
        }
        List<Words> typeWords = typedWordsDTO.getTypeWords();
        Map<String, TypeStatusCount> map = new HashMap<>();
        for (Words typeWord : typeWords) {
            TypeStatusCount typeStatusCount = map.computeIfAbsent(typeWord.mergeWords(), e -> new TypeStatusCount());
            typeStatusCount.incr(this.getWordsStatus(typeWord));
        }

        // 保存整体详情到mongo
        typeWordsRepository.save(typeWordsMapStruct.dtoToMongo(typedWordsDTO));


        // 解析打词计数，记录到mongo
        List<TypeStatusCountMongo> oldTypeStatusCountMongoList = typeStatusCountRepository.findTypeStatusCountByUserIdAndWordIn(userId, map.keySet());
        Map<String, TypeStatusCountMongo> oldTypeStatusCountMongoMap = oldTypeStatusCountMongoList.stream()
                .collect(Collectors.toMap(TypeStatusCountMongo::getWord, typeStatusCountMongo -> typeStatusCountMongo));

        List<TypeStatusCountMongo> statusCountMongoList = new LinkedList<>();
        for (Map.Entry<String, TypeStatusCount> entry : map.entrySet()) {
            TypeStatusCountMongo oldTypeStatusCountMongo = oldTypeStatusCountMongoMap.get(entry.getKey());
            TypeStatusCount typeStatusCount = entry.getValue();
            if (oldTypeStatusCountMongo == null) {
                oldTypeStatusCountMongo = BeanUtil.copyProperties(typeStatusCount, TypeStatusCountMongo.class);
            } else {
                TypeStatusCount oldTypeStatusCount = BeanUtil.copyProperties(oldTypeStatusCountMongo, TypeStatusCount.class);
                oldTypeStatusCount.incr(typeStatusCount);
                BeanUtil.copyProperties(oldTypeStatusCount, oldTypeStatusCountMongo);
            }
            oldTypeStatusCountMongo.setUserId(userId);
            oldTypeStatusCountMongo.setWord(entry.getKey());
            statusCountMongoList.add(oldTypeStatusCountMongo);
        }
        typeStatusCountRepository.saveAll(statusCountMongoList);
    }

    public TypedWordsDTO getTypingWordsDetail(Long historyId, Long userId) {
        TypingHistory typingHistory = typingHistoryService.getById(historyId);
        if (typingHistory == null) {
            throw new LBRuntimeException(Code203History.NOT_EXIST_ARTICLE);
        }
        if (!Objects.equals(userId, typingHistory.getUserId())) {
            throw new LBRuntimeException(Code203History.AUTH_FAIL_ARTICLE);
        }
        TypedWordsMongo typedWordsMongo = typeWordsRepository.getTypeWordsMongoByHistoryId(historyId);

        if (typedWordsMongo == null) {
            throw new LBRuntimeException(Code203History.NOT_EXIST_TYPED_WORDS);
        }
        return typeWordsMapStruct.mongoToDto(typedWordsMongo);
    }

    private WordsDetailStatus getWordsStatus(Words typeWord) {
        List<TypeChar> wordsChar = typeWord.getWordsChar();
        List<TypeChar> codesChar = typeWord.getCodesChar();
        boolean mistake = wordsChar.stream().anyMatch(TypeChar::getMistake);
        boolean result = wordsChar.stream().noneMatch(typeChar -> typeChar.getMistake() && typeChar.getDeleteTime() != null);
        String words = typeWord.mergeWords();
        String codes = typeWord.mergeCodes();

        Boolean wordRight = !mistake && result;
        long wordDelete = wordsChar.stream().filter(typeChar -> typeChar.getDeleteTime() != null).count();
        TypingWordsTypeEnum wordType = TypingWordsTypeEnum.compare(words, typeWord.getWordTips());
        long codeDelete = codesChar.stream().filter(typeChar -> typeChar.getDeleteTime() != null).count();
        TypingWordsTypeEnum codeType = TypingWordsTypeEnum.compare(codes, typeWord.getCodeTips());
        return new WordsDetailStatus(wordRight, wordDelete, wordType, codeDelete, codeType);
    }

    @Data
    @AllArgsConstructor
    public static class WordsDetailStatus {

        //词组状态：正确，错误
        private Boolean wordRight;

        //词组删改状态
        private long wordDelete;

        //词组对比状态
        private TypingWordsTypeEnum wordType;

        //编码删改状态
        private long codeDelete;

        //编码对比状态
        private TypingWordsTypeEnum codeType;

        public boolean perfect() {
            return wordRight() &&
                    codeRight() &&
                    wordType == TypingWordsTypeEnum.RIGHT &&
                    codeType == TypingWordsTypeEnum.RIGHT;
        }

        //回改
        public boolean wordRight() {
            return wordRight && wordDelete == 0;
        }

        //退格
        public boolean codeRight() {
            return codeDelete == 0;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TypeStatusCount {

        private int count;

        private int codeRight;

        private int wordRight;

        private int codeWrong;

        private int wordWrong;

        private int perfect;

        public void incr(WordsDetailStatus wordsStatus) {
            count++;
            if (wordsStatus.codeRight()) {
                codeRight++;
            } else {
                codeWrong++;
            }
            if (wordsStatus.wordRight()) {
                wordRight++;
            } else {
                wordWrong++;
            }
            if (wordsStatus.perfect()) {
                perfect++;
            }
        }

        public void incr(int count, int codeRight, int wordRight, int codeWrong, int wordWrong, int perfect) {
            this.count += count;
            this.codeRight += codeRight;
            this.wordRight += wordRight;
            this.codeWrong += codeWrong;
            this.wordWrong += wordWrong;
            this.perfect += perfect;
        }

        public void incr(TypeStatusCount typeStatusCount) {
            this.count += typeStatusCount.count;
            this.codeRight += typeStatusCount.codeRight;
            this.wordRight += typeStatusCount.wordRight;
            this.codeWrong += typeStatusCount.codeWrong;
            this.wordWrong += typeStatusCount.wordWrong;
            this.perfect += typeStatusCount.perfect;
        }
    }


}
