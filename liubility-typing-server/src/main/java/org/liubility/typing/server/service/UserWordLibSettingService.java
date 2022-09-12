package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.domain.dto.UserWordLibSettingDTO;
import org.liubility.typing.server.domain.entity.UserWordLibSetting;
import org.liubility.typing.server.domain.vo.UserWordSettingListPageVO;
import org.liubility.typing.server.domain.vo.WordLibListPageVO;

/**
 * @Author: JDragon
 * @Data:2022/9/12 13:53
 * @Description:
 */
public interface UserWordLibSettingService extends IService<UserWordLibSetting> {

    UserWordLibSetting getUserDefaultUserSetting(Long userId);

    void wordLibSetting(UserWordLibSettingDTO wordLibSettingDTO);

    IPage<UserWordSettingListPageVO> getPageByUserId(IPage<UserWordSettingListPageVO> iPage, Long userId);

    TrieWordParser getCache(Long userId);

    void cache(Long userId, TrieWordParser parser);

    void rmCache(Long userId);
}
