package org.liubility.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.account.domain.entity.Article;
import org.liubility.account.domain.entity.TypeHistory;
import org.liubility.account.mappers.TypeHistoryMapper;
import org.liubility.account.mapstruct.ArticleMapStruct;
import org.liubility.account.mapstruct.TypeHistoryMapStruct;
import org.liubility.account.service.ArticleService;
import org.liubility.account.service.TypeHistoryService;
import org.liubility.commons.dto.account.ArticleDto;
import org.liubility.commons.dto.account.HistoryArticleDto;
import org.liubility.commons.dto.account.TypeHistoryDto;
import org.liubility.commons.exception.LBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 11:52
 * @Email 1061917196@qq.com
 * @Des:
 */

@Service
public class TypeHistoryServiceImpl extends ServiceImpl<TypeHistoryMapper, TypeHistory> implements TypeHistoryService {

    @Autowired
    private TypeHistoryMapStruct typeHistoryMapStruct;

    @Autowired
    private ArticleMapStruct articleMapStruct;

    @Autowired
    private ArticleService articleService;

    @Override
    public IPage<TypeHistoryDto> getTypeHistoryByUserId(IPage<TypeHistory> historyIPage, Integer userId) {
        LambdaQueryWrapper<TypeHistory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TypeHistory::getUserId, userId);
        IPage<TypeHistory> page = this.page(historyIPage, lambdaQueryWrapper);
        return typeHistoryMapStruct.toDtoPage(page);
    }

    @Override
    public String uploadHistoryAndArticle(HistoryArticleDto historyArticleDto) throws LBException {
        TypeHistory typeHistory = typeHistoryMapStruct.toEntity(historyArticleDto.getTypeHistoryDto());
        Article article = articleMapStruct.toEntity(historyArticleDto.getArticleDto());

        if(typeHistory.getTime()<0){
            throw new LBException("成绩出现异常");
        }

        Article oldArticle = articleService.getArticle(article);
        if (oldArticle == null) {
            if (article.insert()) {
                oldArticle = article;
            } else {
                throw new LBException("保存文档失败");
            }
        }

        typeHistory.setArticleId(oldArticle.getId());
        if (typeHistory.getParagraph() == 0) {
            typeHistory.setParagraph(1);
        }

        if (typeHistory.insert()) {
            return "上传成功";
        }else{
            throw new LBException("上传失败");
        }
    }
}
