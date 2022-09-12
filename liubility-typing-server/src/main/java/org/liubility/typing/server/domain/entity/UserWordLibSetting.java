package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

/**
 * @Author: JDragon
 * @Data:2022/9/12 13:51
 * @Description:
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_word_lib_setting")
public class UserWordLibSetting extends Model<UserWordLibSetting> {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long wordLibId;

    private String keyBoardPartition;

    private Double duplicateSymbolWeight;

    private Double wordLengthWeight;

    private Boolean defaultLib;
}
