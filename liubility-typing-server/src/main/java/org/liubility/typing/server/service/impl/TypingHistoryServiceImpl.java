package org.liubility.typing.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liubility.typing.server.domain.entity.Article;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.typing.server.enums.exception.Code203History;
import org.liubility.typing.server.mappers.TypingHistoryMapper;
import org.liubility.typing.server.mapstruct.ArticleMapStruct;
import org.liubility.typing.server.mapstruct.TypeHistoryMapStruct;
import org.liubility.typing.server.service.ArticleService;
import org.liubility.typing.server.service.TypingHistoryService;
import org.liubility.commons.dto.account.HistoryArticleDto;
import org.liubility.commons.dto.account.TypeHistoryDto;
import org.liubility.commons.exception.LBRuntimeException;
import org.springframework.stereotype.Service;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 11:52
 * @Email 1061917196@qq.com
 * @Des:
 */

@Service
public class TypingHistoryServiceImpl extends ServiceImpl<TypingHistoryMapper, TypingHistory> implements TypingHistoryService {

    private final TypeHistoryMapStruct typeHistoryMapStruct;

    private final ArticleMapStruct articleMapStruct;

    private final ArticleService articleService;

    public TypingHistoryServiceImpl(TypeHistoryMapStruct typeHistoryMapStruct, ArticleMapStruct articleMapStruct, ArticleService articleService) {
        this.typeHistoryMapStruct = typeHistoryMapStruct;
        this.articleMapStruct = articleMapStruct;
        this.articleService = articleService;
    }

    @Override
    public IPage<TypeHistoryDto> getTypeHistoryByUserId(IPage<TypingHistory> iPage, Long userId) {
        LambdaQueryWrapper<TypingHistory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TypingHistory::getUserId, userId);
        IPage<TypingHistory> page = this.page(iPage, lambdaQueryWrapper);
        return typeHistoryMapStruct.toDtoPage(page);
    }

    @Override
    public String uploadHistoryAndArticle(HistoryArticleDto historyArticleDto) {
        TypingHistory typingHistory = typeHistoryMapStruct.toEntity(historyArticleDto.getTypeHistoryDto());
        Article article = articleMapStruct.toEntity(historyArticleDto.getArticleDto());

        if (typingHistory.getTime() < 0) {
            throw new LBRuntimeException(Code203History.ABNORMAL_GRADES);
        }

        Article oldArticle = articleService.getArticle(article);
        if (oldArticle == null) {
            if (article.insert()) {
                oldArticle = article;
            } else {
                throw new LBRuntimeException(Code203History.SAVE_ARTICLE_FAIL);
            }
        }

        typingHistory.setArticleId(oldArticle.getId());
        if (typingHistory.getParagraph() == 0) {
            typingHistory.setParagraph(1);
        }

        if (typingHistory.insert()) {
            return "上传成功";
        } else {
            throw new UnknownError("上传失败");
        }
    }
}
