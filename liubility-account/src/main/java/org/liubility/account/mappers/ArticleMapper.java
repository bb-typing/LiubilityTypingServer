package org.liubility.account.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.liubility.account.domain.entity.Article;
import org.springframework.stereotype.Repository;

/**
 * @Author JDragon
 * @Date 2021.02.20 下午 12:42
 * @Email 1061917196@qq.com
 * @Des:
 */
@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {
}
