package org.liubility.typing.server.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.liubility.typing.server.domain.entity.UserWordLibSetting;
import org.liubility.typing.server.domain.vo.UserWordSettingListPageVO;
import org.springframework.stereotype.Repository;

/**
 * @Author: JDragon
 * @Data:2022/9/12 13:52
 * @Description:
 */

@Repository
@Mapper
public interface UserWordLibSettingMapper extends BaseMapper<UserWordLibSetting> {
    Page<UserWordSettingListPageVO> getPageByUserId(IPage<UserWordSettingListPageVO> page, @Param("userId") Long userId);
}
