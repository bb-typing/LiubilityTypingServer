package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author JDragon
 * @Date 2021.08.29 下午 10:24
 * @Email 1061917196@qq.com
 * @Des:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("typing_match")
public class TypingMatch extends Model<TypingMatch> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date holdDate;

    private Integer articleId;

    private String author;

    private Integer matchType;

    public TypingMatch(Integer articleId, Date holdDate, String author,Integer matchType){
        this.articleId = articleId;
        this.holdDate = holdDate;
        this.author = author;
        this.matchType = matchType;
    }
}
