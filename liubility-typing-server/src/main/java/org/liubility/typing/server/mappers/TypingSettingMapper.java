package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.liubility.typing.server.domain.entity.TypingSetting;
import org.springframework.stereotype.Repository;

/**
 * @Author: JDragon
 * @Data:2022/9/30 20:38
 * @Description:
 */
@Mapper
@Repository
public interface TypingSettingMapper extends BaseMapper<TypingSetting> {
    String getUserTypingSettingByType(@Param("type") String type, @Param("userId") Long userId);

    void updateUserTypingSettingByType(@Param("type") String type,
                                       @Param("userId") Long userId,
                                       @Param("content") String content);
}
