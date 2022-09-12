package org.liubility.typing.server.mapstruct;

import org.liubility.typing.server.domain.dto.UserWordLibSettingDTO;
import org.liubility.typing.server.domain.entity.UserWordLibSetting;
import org.mapstruct.Mapper;

/**
 * @Author: JDragon
 * @Data:2022/9/12 14:44
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface UserWordLibSettingMapStruct {

    UserWordLibSetting dto2Entity(UserWordLibSettingDTO userWordLibSettingDTO);

}
