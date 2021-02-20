package org.liubility.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.account.domain.entity.Article;
import org.liubility.account.mappers.ArticleMapper;
import org.liubility.account.service.ArticleService;
import org.liubility.commons.exception.LBException;
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
    public Article getArticleById(Integer articleId) throws LBException {
        Article article = getArticle(new Article(articleId));
        if(article == null){
            throw new LBException("没有该文章");
        }
        return article;
    }


}
