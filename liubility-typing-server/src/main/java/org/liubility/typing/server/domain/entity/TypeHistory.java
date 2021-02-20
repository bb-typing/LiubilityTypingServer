package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Date 2021.02.19 下午 11:47
 * @Email 1061917196@qq.com
 * @Des:
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("type_history")
public class TypeHistory extends Model<TypeHistory> {

    @TableId(type = IdType.AUTO)
    private int id;

    private int userId;

    private int articleId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date typeDate;

    private double speed;

    private double keySpeed;

    private double keyLength;

    private int number;

    private int deleteText;

    private int deleteNum;

    private int mistake;

    private int repeatNum;

    private double keyAccuracy;

    private double keyMethod;

    private double wordRate;

    private double time;

    private int paragraph;

    private boolean mobile;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
