package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.liubility.typing.server.domain.entity.TypingMatch;
import org.liubility.typing.server.domain.vo.TypingMatchVO;
import org.springframework.stereotype.Repository;

/**
 * @Author JDragon
 * @Date 2021.08.29 下午 10:16
 * @Email 1061917196@qq.com
 * @Des:
 */
@Mapper
@Repository
public interface TypingMatchMapper extends BaseMapper<TypingMatch> {

    Boolean existMatch(@Param("holdDate") String holdDate);

    TypingMatchVO getTodayMatch(@Param("holdDate") String holdDate);
}
