package org.liubility.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.account.domain.entity.TypeUser;
import org.liubility.commons.exception.LBException;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 8:38
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface TypeUserService extends IService<TypeUser>{
    TypeUser getTypeUserById(Integer userId) throws LBException;
}
