package org.liubility.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.liubility.account.domain.entity.Article;
import org.liubility.account.domain.entity.TypeHistory;
import org.liubility.commons.dto.account.ArticleDto;
import org.liubility.commons.dto.account.HistoryArticleDto;
import org.liubility.commons.dto.account.TypeHistoryDto;
import org.liubility.commons.exception.LBException;

import java.util.List;

/**
 * @Author JDragon
 * @Date 2021.02.19 下午 11:52
 * @Email 1061917196@qq.com
 * @Des:
 */
public interface TypeHistoryService extends IService<TypeHistory> {
    IPage<TypeHistoryDto> getTypeHistoryByUserId(IPage<TypeHistory> historyIPage, Integer userId);

    String uploadHistoryAndArticle(HistoryArticleDto historyArticleDto) throws LBException;
}
