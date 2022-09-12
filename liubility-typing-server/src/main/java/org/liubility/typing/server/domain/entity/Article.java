package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author JDragon
 * @Date 2021.02.20 下午 12:16
 * @Email 1061917196@qq.com
 * @Des:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("all_article")
@NoArgsConstructor
@AllArgsConstructor
public class Article extends Model<Article> {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    public Article(Long id) {
        this.id = id;
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
