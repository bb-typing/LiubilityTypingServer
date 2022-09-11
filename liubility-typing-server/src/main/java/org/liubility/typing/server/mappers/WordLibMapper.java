package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.liubility.typing.server.domain.entity.WordLibInfo;
import org.springframework.stereotype.Repository;

/**
 * @Author: JDragon
 * @Data:2022/9/11 19:10
 * @Description:
 */
@Mapper
@Repository
public interface WordLibMapper extends BaseMapper<WordLibInfo> {
}
