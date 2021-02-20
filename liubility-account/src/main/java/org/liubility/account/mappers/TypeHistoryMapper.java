package org.liubility.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.liubility.account.domain.entity.TypeHistory;
import org.springframework.stereotype.Repository;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 11:51
 * @Email 1061917196@qq.com
 * @Des:
 */

@Mapper
@Repository
public interface TypeHistoryMapper extends BaseMapper<TypeHistory> {
}
