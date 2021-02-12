package org.liuibility.security.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.liuibility.security.domain.entity.Account;
import org.springframework.stereotype.Repository;

/**
 * @Author JDragon
 * @Date 2021.02.11 上午 12:54
 * @Email 1061917196@qq.com
 * @Des:
 */

@Mapper
@Repository
public interface AccountMapper extends BaseMapper<Account> {

}
