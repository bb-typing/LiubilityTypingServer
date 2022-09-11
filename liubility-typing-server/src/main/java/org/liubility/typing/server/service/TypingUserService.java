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
public interface TypingUserService extends IService<TypingUser>{
    TypingUser getTypeUserById(Long userId);

    NumDto changeNum(Long userId,NumDto numDto);
}
