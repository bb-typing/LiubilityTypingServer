package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.commons.dto.account.NumDto;
import org.liubility.typing.server.domain.entity.TypingUser;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 8:38
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface TypingUserService extends IService<TypingUser> {
    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    TypingUser getTypeUserById(Long userId);

    /**
     * 更改用户字数
     *
     * @param userId 用户id
     * @param numDto 变更字数
     * @return 更改后字数
     */
    NumDto changeNum(Long userId, NumDto numDto);
}
