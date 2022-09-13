package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.typing.server.domain.dto.WordLibDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: JDragon
 * @Data:2022/9/14 0:23
 * @Description:
 */

@RestController
@RequestMapping("/wordLib/community")
@Api(tags = "词库社区")
public class WordLibCommunityController {

    @PutMapping
    @ApiOperation("上传词库文件")
    public Result<String> uploadWordLib() {

        return Result.success("上传成功");
    }

}
