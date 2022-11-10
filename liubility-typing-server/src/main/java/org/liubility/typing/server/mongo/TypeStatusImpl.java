package org.liubility.typing.server.mongo;

import org.liubility.typing.server.mongo.entity.TypeStatusCountMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/11/9 21:09
 * @Description:
 */
@Service
public class TypeStatusImpl {

    @Autowired
    private TypeStatusCountRepository typeStatusCountRepository;

    public List<TypeStatusCountMongo> test(Long userId, Integer codeRight, Integer limit) {
        return typeStatusCountRepository.findTypeStatusByUserIdDescTopNLimitN(userId, codeRight, limit);
    }

}
