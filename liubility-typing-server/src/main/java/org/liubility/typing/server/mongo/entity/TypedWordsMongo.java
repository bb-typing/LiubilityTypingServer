package org.liubility.typing.server.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.liubility.typing.server.domain.dto.Words;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/11/10 22:55
 * @Description:
 */
@Data
@Document("history_typed_words")
@NoArgsConstructor
@AllArgsConstructor
public class TypedWordsMongo {

    @MongoId(value = FieldType.INT64)
    private Long historyId;

    private List<Words> typeWords;

    private String typeCodes;
}
