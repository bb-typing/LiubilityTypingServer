package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.typing.server.code.parse.TrieWordParser;
import org.liubility.typing.server.domain.dto.UserWordLibSettingDTO;
import org.liubility.typing.server.domain.entity.UserWordLibSetting;
import org.liubility.typing.server.domain.vo.UserWordSettingListPageVO;

/**
 * @Author: JDragon
 * @Data:2022/9/12 13:53
 * @Description:
 */
public interface UserWordLibSettingService extends IService<UserWordLibSetting> {

    /**
     * 获取用户默认词库配置
     *
     * @param userId 用户id
     * @return 词库配置
     */
    UserWordLibSetting getUserDefaultUserSetting(Long userId);

    /**
     * 新增或更改用户词库配置
     *
     * @param wordLibSettingDTO 词库配置
     */
    void wordLibSetting(UserWordLibSettingDTO wordLibSettingDTO);

    /**
     * 分页获取用户词库配置
     *
     * @param iPage  分页参数
     * @param userId 用户id
     * @return 词库配置分页
     */
    IPage<UserWordSettingListPageVO> getPageByUserId(IPage<UserWordSettingListPageVO> iPage, Long userId);

    /**
     * 根据用户id获取缓存parser
     *
     * @param userId 用户id
     * @return TrieWordParser
     */
    TrieWordParser getCache(Long userId);

    /**
     * 根据用户id缓存parser
     *
     * @param userId 用户id
     * @param parser TrieWordParser
     */
    void cache(Long userId, TrieWordParser parser);

    /**
     * 根据用户id移除缓存parser
     *
     * @param userId 用户id
     */
    void rmCache(Long userId);

    /**
     * 删除配置，删除之后会清理parser缓存
     *
     * @param settingId 配置id
     * @param userId    用户id
     */
    void deleteSetting(Long settingId, Long userId);
}
