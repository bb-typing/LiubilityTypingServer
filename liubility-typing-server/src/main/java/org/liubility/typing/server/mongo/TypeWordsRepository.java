package org.liubility.typing.server.mongo;

import org.liubility.typing.server.mongo.entity.TypedWordsMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: JDragon
 * @Data:2022/11/10 22:57
 * @Description:
 */
public interface TypeWordsRepository extends MongoRepository<TypedWordsMongo, Long> {

    TypedWordsMongo getTypeWordsMongoByHistoryId(Long historyId);

}
