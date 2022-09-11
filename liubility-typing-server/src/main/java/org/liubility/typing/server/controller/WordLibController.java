package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.commons.controller.BaseController;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.typing.server.domain.dto.WordLibDTO;
import org.liubility.typing.server.service.WordLibService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: JDragon
 * @Data:2022/9/10 1:29
 * @Description:
 */
@RestController
@RequestMapping("/wordLib")
@Api(tags = "个人中心")
public class WordLibController extends BaseController {

    private final WordLibService wordLibService;

    public WordLibController(WordLibService wordLibService) {
        this.wordLibService = wordLibService;
    }

    @PostMapping(value = "/uploadWordLib")
    @ApiOperation("上传词库文件")
    public Result<String> uploadWordLib(WordLibDTO wordLibDTO) {
        wordLibService.uploadWordLib(getUserId(), wordLibDTO);
        return Result.success("上传成功");
    }
}
