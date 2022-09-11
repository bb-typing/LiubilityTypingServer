package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>跟打器后端版本</p>
 * <p>create time: 2021/10/5 16:19 </p>
 *
 * @author : Jdragon
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("typing_version")
public class TypingVersion extends Model<TypingVersion> {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String appVersion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
