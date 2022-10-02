package org.liubility.typing.server.service;

import com.alibaba.fastjson.JSONObject;
import org.liubility.typing.server.domain.entity.TypingSetting;
import org.liubility.typing.server.enums.TypingSettingTypeEnum;

/**
 * @Author: JDragon
 * @Data:2022/9/30 20:39
 * @Description:
 */
public interface TypingSettingService extends CommonService<TypingSetting> {

    JSONObject getTypingSetting(TypingSettingTypeEnum typingSettingTypeEnum, Long userId);

    void setTypingSetting(TypingSettingTypeEnum typingSettingTypeEnum, Long userId, String content);
}
