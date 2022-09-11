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
    Article getArticle(Article article);

    Article getArticleById(Long articleId);
}
