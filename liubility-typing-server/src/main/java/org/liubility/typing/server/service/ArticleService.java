package org.liubility.typing.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.typing.server.domain.entity.Article;

/**
 * @Author JDragon
 * @Date 2021.02.20 下午 12:42
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface ArticleService extends IService<Article> {
    /**
     * 从数据库中获取文章
     *
     * @param article 条件
     * @return Article
     */
    Article getArticle(Article article);

    /**
     * 根据文章id获取文章
     *
     * @param articleId 文章id
     * @return Article
     */
    Article getArticleById(Long articleId);
}
