package org.liubility.typing.server.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.liubility.typing.server.handler.TypingWordsDetailHandler;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @Author: JDragon
 * @Data:2022/11/9 20:54
 * @Description:
 */
@Data
@Document("all_data_test")
@NoArgsConstructor
@AllArgsConstructor
public class TypeStatusCountMongo {

    @MongoId(value = FieldType.OBJECT_ID)
    private String id;

    private long userId;

    private String word;

    private int count;

    private int codeRight;

    private int wordRight;

    private int codeWrong;

    private int wordWrong;

    private int perfect;
}