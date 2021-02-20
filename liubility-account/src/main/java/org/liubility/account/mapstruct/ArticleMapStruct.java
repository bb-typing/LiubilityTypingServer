package org.liubility.account.mapstruct;

import org.liubility.account.domain.entity.Article;
import org.liubility.commons.dto.account.ArticleDto;
import org.mapstruct.Mapper;

/**
 * @Author JDragon
 * @Date 2021.02.20 下午 12:30
 * @Email 1061917196@qq.com
 * @Des:
 */

@Mapper(componentModel = "spring")
public interface ArticleMapStruct {
    Article toEntity(ArticleDto articleDto);
}
