package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 8:40
 * @Email 1061917196@qq.com
 * @Des:
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("type_user")
public class TypeUser extends Model<TypeUser> {
    @TableId
    private int id;

    private int num;

    private int rightNum;

    private int misNum;

    private int dateNum;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date regDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date lastLoginDate;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
