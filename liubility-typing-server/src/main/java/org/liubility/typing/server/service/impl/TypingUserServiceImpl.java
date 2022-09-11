package org.liubility.typing.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.commons.dto.account.NumDto;
import org.liubility.commons.util.GenerateUtils;
import org.liubility.typing.server.domain.entity.TypingUser;
import org.liubility.typing.server.exception.UserTypingCode;
import org.liubility.typing.server.mappers.TypingUserMapper;
import org.liubility.typing.server.mapstruct.TypeUserMapStruct;
import org.liubility.typing.server.service.TypingUserService;
import org.liubility.commons.exception.LBRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 8:39
 * @Email 1061917196@qq.com
 * @Des:
 */

@Service
public class TypingUserServiceImpl extends ServiceImpl<TypingUserMapper, TypingUser> implements TypingUserService {

    @Value("${secret}")
    private String secret;

    @Autowired
    private TypeUserMapStruct typeUserMapStruct;

    @Override
    public TypingUser getTypeUserById(Long userId) {
        LambdaQueryWrapper<TypingUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TypingUser::getId, userId);
        TypingUser typingUser = this.getOne(lambdaQueryWrapper);
        if (typingUser == null) {
            throw new LBRuntimeException(UserTypingCode.NOT_EXIST);
        }
        return typingUser;
    }

    @Override
    public NumDto changeNum(Long userId, NumDto numDto) {
        String md5 = GenerateUtils.generateNumKey(numDto, secret);
        if (!md5.equals(numDto.getNumKey())) {
            throw new LBRuntimeException(UserTypingCode.SECRET_ERROR);
        }
        TypingUser typingUser = getTypeUserById(userId);
        typingUser.incrNum(numDto);
        this.updateById(typingUser);
        return typeUserMapStruct.getUserNum(typingUser);
    }
}
