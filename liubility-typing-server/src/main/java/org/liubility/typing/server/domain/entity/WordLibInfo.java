package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private Long userId;

    private String wordLibPath;

    private Integer storageType;

    private Integer wordCount;

    private Integer wordMaxLength;

    private Integer codeMaxLength;

    private String duplicateSymbols;

    private String keyBoardPartition;

    private Double duplicateSymbolWeight;

    private Double wordLengthWeight;

    private String leaderSymbols;

    private Integer priority;
}
