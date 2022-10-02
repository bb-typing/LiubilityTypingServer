package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: JDragon
 * @Data:2022/10/2 21:17
 * @Description:
 */
@Mapper
@Repository
public interface CommonMapper extends BaseMapper<CommonModel> {
}
