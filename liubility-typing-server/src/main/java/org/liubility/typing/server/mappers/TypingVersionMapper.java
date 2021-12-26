package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.liubility.typing.server.domain.entity.TypingVersion;
import org.springframework.stereotype.Repository;

/**
 * <p></p>
 * <p>create time: 2021/10/6 20:27 </p>
 *
 * @author : Jdragon
 */

@Repository
@Mapper
public interface TypingVersionMapper extends BaseMapper<TypingVersion> {
    TypingVersion selectNewVersion();
}
