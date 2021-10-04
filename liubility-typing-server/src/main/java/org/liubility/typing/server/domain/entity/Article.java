package org.liubility.typing.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    public Article(int id) {
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
