package org.liubility.typing.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.commons.util.TimingMap;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.config.CodeConfig;
import org.liubility.typing.server.domain.dto.UserWordLibSettingDTO;
import org.liubility.typing.server.domain.entity.UserWordLibSetting;
import org.liubility.typing.server.domain.vo.UserWordSettingListPageVO;
import org.liubility.typing.server.enums.exception.WordLibCode;
import org.liubility.typing.server.mappers.UserWordLibSettingMapper;
import org.liubility.typing.server.mapstruct.UserWordLibSettingMapStruct;
import org.liubility.typing.server.service.UserWordLibSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/9/12 13:53
 * @Description:
 */
@Slf4j
@Service
public class UserWordLibSettingServiceImpl extends ServiceImpl<UserWordLibSettingMapper, UserWordLibSetting> implements UserWordLibSettingService {

    @Resource
    private TimingMap<Long, TrieWordParser> wordParserCache;

    private final UserWordLibSettingMapStruct mapStruct;

    public UserWordLibSettingServiceImpl(UserWordLibSettingMapStruct mapStruct) {
        this.mapStruct = mapStruct;
    }

    public UserWordLibSetting selectOneByEntity(UserWordLibSetting cond) {
        LambdaQueryWrapper<UserWordLibSetting> wordLibInfoLambdaQueryWrapper = new LambdaQueryWrapper<>(cond);
        return getOne(wordLibInfoLambdaQueryWrapper);
    }

    public List<UserWordLibSetting> selectByEntity(UserWordLibSetting cond) {
        LambdaQueryWrapper<UserWordLibSetting> wordLibInfoLambdaQueryWrapper = new LambdaQueryWrapper<>(cond);
        return list(wordLibInfoLambdaQueryWrapper);
    }

    @Override
    public UserWordLibSetting getUserDefaultUserSetting(Long userId) {
        UserWordLibSetting userWordLibSetting = getUserDefaultUserSettingNoCheck(userId);
        if (userWordLibSetting == null) {
            throw new LBRuntimeException(WordLibCode.NOT_SET_DEFAULT_WORD_LIB);
        }
        return userWordLibSetting;
    }

    public UserWordLibSetting getUserDefaultUserSettingNoCheck(Long userId) {
        UserWordLibSetting cond = UserWordLibSetting.builder().userId(userId).defaultLib(true).build();
        return selectOneByEntity(cond);
    }

    @Override
    public void wordLibSetting(UserWordLibSettingDTO wordLibSettingDTO) {
        UserWordLibSetting userWordLibSetting = mapStruct.dto2Entity(wordLibSettingDTO);
        Long id = wordLibSettingDTO.getId();
        if (userWordLibSetting.getDefaultLib()) {
            UserWordLibSetting userDefaultUserSetting = this.getUserDefaultUserSettingNoCheck(userWordLibSetting.getUserId());
            if (userDefaultUserSetting != null && !userDefaultUserSetting.getId().equals(id)) {
                userDefaultUserSetting.setDefaultLib(false);
                userDefaultUserSetting.updateById();
            }
        }
        if (id == null) {
            userWordLibSetting.insert();
        } else {
            userWordLibSetting.updateById();
        }
        this.rmCache(wordLibSettingDTO.getUserId());
    }

    @Override
    public IPage<UserWordSettingListPageVO> getPageByUserId(IPage<UserWordSettingListPageVO> iPage, Long userId) {
        return baseMapper.getPageByUserId(iPage, userId);
    }

    @Override
    public TrieWordParser getCache(Long userId) {
        return wordParserCache.get(userId);
    }

    @Override
    public void cache(Long userId, TrieWordParser parser) {
        wordParserCache.put(userId, parser);
    }

    @Override
    public void rmCache(Long userId) {
        wordParserCache.remove(userId);
    }
}
