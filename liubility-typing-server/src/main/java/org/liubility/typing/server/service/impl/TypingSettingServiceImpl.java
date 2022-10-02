package org.liubility.typing.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.liubility.commons.exception.LBRuntimeException;
import org.liubility.typing.server.domain.entity.TypingSetting;
import org.liubility.typing.server.enums.TypingSettingTypeEnum;
import org.liubility.typing.server.enums.exception.Code208TypingSetting;
import org.liubility.typing.server.mappers.TypingSettingMapper;
import org.liubility.typing.server.service.TypingSettingService;
import org.springframework.stereotype.Service;

/**
 * @Author: JDragon
 * @Data:2022/9/30 20:40
 * @Description:
 */
@Service
public class TypingSettingServiceImpl extends ServiceImpl<TypingSettingMapper, TypingSetting> implements TypingSettingService {
    @Override
    public JSONObject getTypingSetting(TypingSettingTypeEnum typingSettingTypeEnum, Long userId) {
        String userTypingSettingByType = baseMapper.getUserTypingSettingByType(typingSettingTypeEnum.name(), userId);
        if (StringUtils.isBlank(userTypingSettingByType)) {
            return new JSONObject();
        }
        if (!JSONUtil.isJson(userTypingSettingByType)) {
            throw new LBRuntimeException(Code208TypingSetting.VALID_JSON_FAIL);
        }
        return JSONObject.parseObject(userTypingSettingByType);
    }

    @Override
    public void setTypingSetting(TypingSettingTypeEnum typingSettingTypeEnum, Long userId, String content) {
        if (!JSONUtil.isJson(content)) {
            throw new LBRuntimeException(Code208TypingSetting.VALID_JSON_FAIL);
        }
        TypingSetting byUserId = getByUserId(userId);
        if (byUserId == null) {
            TypingSetting typingSetting = new TypingSetting();
            BeanUtil.setFieldValue(typingSetting, typingSettingTypeEnum.name(), content);
            typingSetting.setUserId(userId);
            typingSetting.insert();
        } else {
            baseMapper.updateUserTypingSettingByType(typingSettingTypeEnum.name(), userId, content);
        }
    }
}
