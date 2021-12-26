package org.liubility.typing.server.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

/**
 * <p> 带跟打者姓名的跟打历史 </p>
 * <p>create time: 2021/10/7 1:25 </p>
 *
 * @author : Jdragon
 */

public class TypingHistoryVO {
    private int id;

    private int userId;

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

    private int articleId;

    private int paragraph;

    private boolean isMobile;

    private String userName;
}
