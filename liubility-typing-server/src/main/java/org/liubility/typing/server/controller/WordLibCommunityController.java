package org.liubility.typing.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.liubility.commons.controller.BaseController;
import org.liubility.commons.http.response.normal.Result;
import org.liubility.commons.http.response.table.PageTable;
import org.liubility.commons.http.response.table.TableFactory;
import org.liubility.commons.http.response.table.TableRef;
import org.liubility.typing.server.domain.cond.QueryCommunityWordLibPageCond;
import org.liubility.typing.server.domain.vo.WordLibListPageVO;
import org.liubility.typing.server.service.WordLibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class WordLibCommunityController extends BaseController {

    @Autowired
    private WordLibService wordLibService;

    @GetMapping("/list")
    @ApiOperation("获取公开的社区词库")
    public Result<PageTable<WordLibListPageVO>> uploadWordLib(QueryCommunityWordLibPageCond wordLibPageCond) {
        IPage<WordLibListPageVO> page = wordLibService.getCommunityWordLibPage(new Page<>(getPageNum(), getPageSize()), wordLibPageCond);
        PageTable<WordLibListPageVO> table = TableFactory.buildPageTable(page, new TableRef<WordLibListPageVO>(page.getRecords()) {
        });
        return Result.success(table);
    }

}
