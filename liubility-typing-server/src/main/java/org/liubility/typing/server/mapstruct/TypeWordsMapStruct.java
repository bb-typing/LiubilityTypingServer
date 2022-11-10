package org.liubility.typing.server.mapstruct;

import org.liubility.typing.server.domain.dto.TypedWordsDTO;
import org.liubility.typing.server.mongo.entity.TypedWordsMongo;
import org.mapstruct.Mapper;

/**
 * @Author: JDragon
 * @Data:2022/11/10 22:59
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface TypeWordsMapStruct {

    TypedWordsMongo dtoToMongo(TypedWordsDTO typedWordsDTO);

    TypedWordsDTO mongoToDto(TypedWordsMongo typedWordsMongo);
}
