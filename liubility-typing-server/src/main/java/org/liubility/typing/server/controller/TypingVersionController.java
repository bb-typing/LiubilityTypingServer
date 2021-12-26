package org.liubility.typing.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.typing.server.domain.entity.TypingVersion;
import org.liubility.typing.server.mappers.TypingVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 * <p>create time: 2021/10/6 20:32 </p>
 *
 * @author : Jdragon
 */
@RestController
@RequestMapping("/version")
@Api(tags = "版本")
public class TypingVersionController {

    @Autowired
    private TypingVersionMapper typingVersionMapper;

    @ApiOperation("获取最新版本号")
    @GetMapping("/newVersion")
    public Result<TypingVersion> getNewVersion() {
        return Result.success(typingVersionMapper.selectNewVersion());
    }
}
