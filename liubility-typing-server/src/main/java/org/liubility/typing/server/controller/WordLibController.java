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
import org.liubility.typing.server.domain.dto.UserWordLibSettingDTO;
import org.liubility.typing.server.domain.dto.WordLibDTO;
import org.liubility.typing.server.domain.vo.TypingTips;
import org.liubility.typing.server.domain.vo.UserWordSettingListPageVO;
import org.liubility.typing.server.domain.vo.WordLibListPageVO;
import org.liubility.typing.server.service.UserWordLibSettingService;
import org.liubility.typing.server.service.WordLibService;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: JDragon
 * @Data:2022/9/10 1:29
 * @Description:
 */
@RestController
@RequestMapping("/wordLib")
@Api(tags = "词库管理")
public class WordLibController extends BaseController {

    private final WordLibService wordLibService;

    private final UserWordLibSettingService userWordLibSettingService;

    public WordLibController(WordLibService wordLibService, UserWordLibSettingService userWordLibSettingService) {
        this.wordLibService = wordLibService;
        this.userWordLibSettingService = userWordLibSettingService;
    }

    @PutMapping
    @ApiOperation("上传词库文件")
    public Result<String> uploadWordLib(WordLibDTO wordLibDTO) {
        wordLibService.uploadWordLib(getUserId(), wordLibDTO);
        return Result.success("上传成功");
    }

    @GetMapping
    @ApiOperation("上传词库列表")
    public Result<PageTable<WordLibListPageVO>> wordLibPage() {
        IPage<WordLibListPageVO> page = wordLibService.getPageByUserId(new Page<>(getPageNum(), getPageSize()), getUserId());
        PageTable<WordLibListPageVO> table = TableFactory.buildPageTable(page, new TableRef<WordLibListPageVO>(page.getRecords()) {
        });
        return Result.success(table);
    }

    @DeleteMapping
    @ApiOperation("删除词库")
    public Result<String> deleteWordLib(@RequestParam Long wordLibId) {
        wordLibService.deleteWordLib(wordLibId);
        return Result.success("删除成功");
    }

    @PutMapping(value = "/setting")
    @ApiOperation("设置词库属性")
    public Result<String> setting(UserWordLibSettingDTO userWordLibSettingDTO) {
        userWordLibSettingDTO.setUserId(getUserId());
        userWordLibSettingService.wordLibSetting(userWordLibSettingDTO);
        return Result.success("设置成功");
    }

    @GetMapping(value = "/setting")
    @ApiOperation("获取词库属性")
    public Result<PageTable<UserWordSettingListPageVO>> getSetting() {
        IPage<UserWordSettingListPageVO> page = userWordLibSettingService.getPageByUserId(new Page<>(getPageNum(), getPageSize()), getUserId());
        PageTable<UserWordSettingListPageVO> table = TableFactory.buildPageTable(page, new TableRef<UserWordSettingListPageVO>(page.getRecords()) {
        });
        return Result.success(table);
    }

    @DeleteMapping(value = "/setting")
    @ApiOperation("删除词库配置")
    public Result<String> deleteSetting(@RequestParam Long settingId) {
        userWordLibSettingService.deleteSetting(settingId, getUserId());
        return Result.success("删除成功");
    }

    @PostMapping(value = "/load")
    @ApiOperation("用户加载云词提")
    public Result<String> loadWordLib() {
        wordLibService.loadParser(getUserId());
        return Result.success("加载成功");
    }

    @PostMapping(value = "/typingTips")
    @ApiOperation("获取词语提示")
    public Result<TypingTips> typingTips(@RequestParam String article) {
        return Result.success(wordLibService.typingTips(getUserId(), article));
    }
}
