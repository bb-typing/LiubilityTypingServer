package org.liubility.typing.server.mapstruct;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.liubility.typing.server.domain.entity.TypingHistory;
import org.liubility.commons.dto.account.TypeHistoryDto;
import org.mapstruct.Mapper;
/**
 * @Author JDragon
 * @Date 2021.02.19 下午 11:59
 * @Email 1061917196@qq.com
 * @Des:
 */

@Mapper(componentModel = "spring")
public interface TypeHistoryMapStruct {

    Page<TypeHistoryDto> toDtoPage(IPage<TypingHistory> histories);

    TypingHistory toEntity(TypeHistoryDto typeHistoryDto);

}
