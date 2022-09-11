package org.liubility.typing.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.typing.server.domain.entity.Article;
import org.liubility.typing.server.mappers.ArticleMapper;
import org.liubility.typing.server.service.ArticleService;
import org.liubility.commons.exception.LBRuntimeException;
import org.springframework.stereotype.Service;

/**
 * @Author JDragon
 * @Date 2021.02.20 下午 12:43
 * @Email 1061917196@qq.com
 * @Des:
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public Article getArticle(Article article) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.setEntity(article);
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public Article getArticleById(Long articleId) {
        Article article = getArticle(new Article(articleId));
        if(article == null){
            throw new LBRuntimeException("没有该文章");
        }
        return article;
    }


}
