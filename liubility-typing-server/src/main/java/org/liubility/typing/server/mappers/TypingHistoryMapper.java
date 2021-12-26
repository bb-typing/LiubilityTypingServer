package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.typing.server.domain.vo.TypingHistoryVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    List<TypingHistoryVO> getTypingMatchHistoryWithName(@Param("typeDate") Date typeDate,
                                                        @Param("matchType") Integer matchType,
                                                        @Param("mobile") Boolean mobile);
}
