package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 11:51
 * @Email 1061917196@qq.com
 * @Des:
 */

@Mapper
@Repository
public interface TypingHistoryMapper extends BaseMapper<TypingHistory> {
    TypingHistory getTypingMatchHistory(@Param("userId") int userId,
                                    @Param("mobile") boolean mobile,
                                    @Param("typeDate") String typeDate);

}
