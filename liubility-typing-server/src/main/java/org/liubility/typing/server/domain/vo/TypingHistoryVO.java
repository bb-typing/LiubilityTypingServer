package org.liubility.typing.server.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.liubility.commons.dto.annotation.TableHeader;
import org.liubility.commons.interceptor.translation.Translate;
import org.liubility.commons.interceptor.translation.Translation;
import org.liubility.typing.server.enums.MobileEnum;

import java.sql.Date;

/**
 * <p> 带跟打者姓名的跟打历史 </p>
 * <p>create time: 2021/10/7 1:25 </p>
 *
 * @author : Jdragon
 */
@Data
@Translation
public class TypingHistoryVO {
    private long id;

    private long userId;

    private long articleId;

    @TableHeader("比赛日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date typeDate;

    @TableHeader("参赛者")
    private String userName;

    @TableHeader("速度")
    private double speed;

    @TableHeader("击键")
    private double keySpeed;

    @TableHeader("码长")
    private double keyLength;

    @TableHeader("字数")
    private int number;

    @TableHeader("回改")
    private int deleteText;

    @TableHeader("退格")
    private int deleteNum;

    @TableHeader("错字")
    private int mistake;

    @TableHeader("选重")
    private int repeatNum;

    @TableHeader("键准")
    private double keyAccuracy;

    @TableHeader("键法")
    private double keyMethod;

    @TableHeader("打词")
    private double wordRate;

    @TableHeader("时间")
    private double time;

    private boolean mobile;

    @TableHeader("设备类型")
    @Translate(dict = MobileEnum.class, byField = "mobile")
    private String mobileCN;
}
