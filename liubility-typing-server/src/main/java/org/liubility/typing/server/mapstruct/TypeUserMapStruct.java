package org.liubility.typing.server.mapstruct;

import org.liubility.commons.dto.account.NumDto;
import org.liubility.typing.server.domain.entity.TypingUser;
import org.mapstruct.Mapper;

/**
 * @Author JDragon
 * @Date 2021.04.29 下午 10:45
 * @Email 1061917196@qq.com
 * @Des:
 */

@Mapper(componentModel = "spring")
public interface TypeUserMapStruct {
    NumDto getUserNum(TypingUser typingUser);
}
