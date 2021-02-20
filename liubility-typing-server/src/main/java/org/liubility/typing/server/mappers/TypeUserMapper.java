package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.liubility.typing.server.domain.entity.TypeUser;
import org.springframework.stereotype.Repository;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 8:42
 * @Email 1061917196@qq.com
 * @Des:
 */
@Mapper
@Repository
public interface TypeUserMapper extends BaseMapper<TypeUser> {
}
