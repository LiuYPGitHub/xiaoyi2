package com.xiaoyi.bis.user.controller;

import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.user.bean.FollowBean;
import com.xiaoyi.bis.user.bean.ReqFollow;
import com.xiaoyi.bis.user.controller.common.ResPageInfo;
import com.xiaoyi.bis.user.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Description：机构关注
 * @Author：kk
 * @Date：2019/8/29 11:39
 */
@Api(tags = "follow", value = "机构关注")
@Slf4j
@Validated
@RestController
@RequestMapping("/web")
public class FollowController extends BaseController {

    private @Autowired
    FollowService followService;

    @ApiOperation(value = "KOL，名师列表", notes = "类型切换 KOL，名师列表")
    @GetMapping("/follow/list")
    public AjaxResult followList(@NotBlank(message = "{required}") @RequestParam String userId,
                                 @NotBlank(message = "{required}") @RequestParam String followType,
                                 @RequestParam("pageNumb") Integer pageNumb, @RequestParam("pageSize") Integer pageSize) throws Exception {

        final ResPageInfo<FollowBean> sysUserInfoList = followService.getFollowList(userId, followType, pageNumb, pageSize);
        return AjaxResult.success(sysUserInfoList);
    }

    @ApiOperation(value = "关注 KOL，名师", notes = "关注 KOL，名师")
    @PostMapping("/follow/create")
    public AjaxResult saveFollow(@RequestBody @Valid ReqFollow reqFollow) {

        log.info("关注 KOL，名师 :{}" + reqFollow);
        followService.addFollow(reqFollow.getUserId(), reqFollow.getFollowType(), reqFollow.getFollowUserId());
        return AjaxResult.success("关注成功");
    }

    @ApiOperation(value = "取消 KOL，名师", notes = "关注 KOL，名师")
    @PostMapping("/follow/remove")
    public AjaxResult unFollow(@NotBlank(message = "{required}")
                               @RequestParam String userId, @NotBlank(message = "{required}")
                               @RequestParam String followUserId) {

        followService.unFollow(userId, followUserId);
        return AjaxResult.success("已取消关注");
    }

    @ApiOperation(value = "取消 KOL，名师", notes = "关注 KOL，名师")
    @PostMapping("/unFollow")
    public AjaxResult unFollow(@NotBlank(message = "{required}") @RequestParam String followId) {

        followService.unFollow(followId);
        return AjaxResult.success("已取消关注");
    }

    @ApiOperation(value = "关注统计", notes = "关注统计")
    @PostMapping("/follow/count")
    public AjaxResult countFollow(@NotBlank(message = "{required}") @RequestParam String userId) {

        final Integer countFollow = followService.countFollow(userId);
        return AjaxResult.success(countFollow);
    }

}
