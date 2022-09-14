package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.liubility.typing.server.domain.cond.QueryCommunityWordLibPageCond;
import org.liubility.typing.server.domain.entity.WordLibInfo;
import org.liubility.typing.server.domain.vo.UserWordSettingListPageVO;
import org.liubility.typing.server.domain.vo.WordLibListPageVO;
import org.springframework.stereotype.Repository;

/**
 * @Author: JDragon
 * @Data:2022/9/11 19:10
 * @Description:
 */
@Mapper
@Repository
public interface WordLibMapper extends BaseMapper<WordLibInfo> {
    Page<WordLibListPageVO> getPageByUserId(IPage<WordLibListPageVO> page, @Param("userId") Long userId);

    Integer countByOriginId(@Param("originId") Long originId);

    Page<WordLibListPageVO> getCommunityWordLibPage(IPage<WordLibListPageVO> page, @Param("cond") QueryCommunityWordLibPageCond cond);
}
