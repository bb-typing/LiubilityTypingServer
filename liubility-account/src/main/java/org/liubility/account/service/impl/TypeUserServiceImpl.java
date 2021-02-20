package org.liubility.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.account.domain.entity.TypeHistory;
import org.liubility.account.domain.entity.TypeUser;
import org.liubility.account.mappers.TypeHistoryMapper;
import org.liubility.account.mappers.TypeUserMapper;
import org.liubility.account.service.TypeHistoryService;
import org.liubility.account.service.TypeUserService;
import org.liubility.commons.dto.account.TypeHistoryDto;
import org.liubility.commons.exception.LBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 8:39
 * @Email 1061917196@qq.com
 * @Des:
 */

@Service
public class TypeUserServiceImpl extends ServiceImpl<TypeUserMapper, TypeUser> implements TypeUserService {

    @Override
    public TypeUser getTypeUserById(Integer userId) throws LBException {
        LambdaQueryWrapper<TypeUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TypeUser::getId, userId);
        TypeUser typeUser = this.getOne(lambdaQueryWrapper);
        if(typeUser == null){
            throw new LBException("你还未申请跟打器账号");
        }
        return typeUser;
    }
}
