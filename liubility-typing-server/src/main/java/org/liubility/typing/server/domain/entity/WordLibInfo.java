package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

/**
 * @Author: JDragon
 * @Data:2022/9/11 19:07
 * @Description:
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("word_lib_info")
public class WordLibInfo extends Model<WordLibInfo> {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private Long originId;

    private Long userId;

    private String wordLibName;

    private String wordLibPath;

    private Integer storageType;

    private Integer wordCount;

    private Integer wordMaxLength;

    private Integer codeMaxLength;

    private String leaderSymbols;

    private String duplicateSymbols;
}
