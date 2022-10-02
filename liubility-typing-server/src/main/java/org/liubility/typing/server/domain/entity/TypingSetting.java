package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.liubility.typing.server.service.CommonModel;


/**
 * @Author: JDragon
 * @Data:2022/9/30 20:35
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("typing_setting")
public class TypingSetting extends CommonModel<TypingSetting> {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private String shortcut;

    private String layout;

    private String hue;
}
