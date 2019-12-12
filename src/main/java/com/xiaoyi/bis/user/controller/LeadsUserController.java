package com.xiaoyi.bis.user.controller;

import com.alibaba.fastjson.JSON;
import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.dict.domain.Dict;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.common.exception.FebsException;
import com.xiaoyi.bis.common.service.RedisService;
import com.xiaoyi.bis.common.utils.JsonMapper;
import com.xiaoyi.bis.user.bean.*;
import com.xiaoyi.bis.user.domain.LeadsUser;
import com.xiaoyi.bis.user.service.LeadsUserInfoService;
import com.xiaoyi.bis.user.service.LeadsUserService;
import com.xiaoyi.bis.user.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description：用户
 * @Author：kk
 * @Date：2019/8/23 13:21
 */
@Api(tags = "sysUser", value = "账号信息")
@Slf4j
@Validated
@RestController
@RequestMapping("/web")
public class LeadsUserController extends BaseController {

    private @Autowired
    LeadsUserService leadsUserService;
    private @Autowired
    LeadsUserInfoService leadsUserInfoService;
    private @Autowired
    RedisService redisService;
    private @Autowired
    SmsService smsService;
    @Autowired
    private Mapper mapper;


    /**
     * 选择身份
     *
     * @param codeId
     * @throws Exception
     */
    @ApiOperation(value = "选择身份", notes = "选择身份（kol,名师,机构）")
    @PostMapping("/chooseType")
    public AjaxResult chooseType(@NotBlank(message = "{required}") @RequestParam String userId,
                                 @NotBlank(message = "{required}") @RequestParam String codeId) throws Exception {

        leadsUserService.chooseType(userId, codeId);
        final LoginUserBean loginUser = leadsUserService.getLoginUser(userId);
        return AjaxResult.success(loginUser);
    }

    /**
     * 修改密码用户状态
     *
     * @param telNum
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "修改密码", notes = "登录校验用户状态")
    @PostMapping("update/check")
    @ResponseBody
    public AjaxResult checkLogin(@NotBlank(message = "{required}") @RequestParam String telNum) {
        final LeadsUser byTel = leadsUserService.findByTel(telNum);
        if (byTel == null) {
            return AjaxResult.error("该账号尚未注册！请先注册");
        }
        return AjaxResult.success("成功");
    }

    /**
     * 修改密码
     *
     * @param telNum
     * @param password
     * @throws FebsException
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PutMapping("password/update")
    public AjaxResult updatePassword(@NotBlank(message = "{required}") @RequestParam String telNum,
                                     @NotBlank(message = "{required}") @RequestParam String password,
                                     @NotBlank(message = "{required}") @RequestParam String code) {
        try {
            String pCode = redisService.get(telNum);
            if (pCode == null)
                return AjaxResult.error("验证码已失效！请重新获取！");
            if (!StringUtils.equals(code, pCode))
                return AjaxResult.error("请输入正确的验证码！");

            final boolean b = leadsUserService.updatePassword(telNum, password);
            if (b) {
                return AjaxResult.success("密码修改成功！");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return AjaxResult.error("修改密码失败！");

    }

    /**
     * 获取验证码
     *
     * @param telNum
     * @return
     * @throws FebsException
     */
    @Deprecated
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @PostMapping("getSmsCode")
    public AjaxResult getSmsCode(@NotBlank(message = "{required}") @RequestParam String telNum) throws FebsException {

        log.info("获取验证码入参:{}" + telNum);
        final LeadsUser byTel = leadsUserService.findByTel(telNum);
        if (byTel != null) {
            try {
                int smsCode = (int) ((Math.random() * 9 + 1) * 100000);
                final String s = redisService.get(telNum);
                if (StringUtils.isEmpty(s)) {
                    final String code = smsService.sendSms(telNum, smsCode);
                    redisService.set(telNum, String.valueOf(smsCode), (long) 1800000);
                    if (StringUtils.equals("2", code)) {
                        return AjaxResult.success("验证码已发送");
                    } else if (StringUtils.equals("403", code)) {
                        return AjaxResult.error("手机号码不能为空");
                    } else if (StringUtils.equals("4030", code)) {
                        return AjaxResult.error("手机号码已被列入黑名单");
                    } else if (StringUtils.equals("4085", code)) {
                        return AjaxResult.error("同一手机号验证码短信发送超出【5】条");
                    } else {
                        return AjaxResult.error("验证码发送失败");
                    }
                }
                return AjaxResult.success("验证码已发送", StringUtils.isEmpty(s) ? smsCode : s);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return AjaxResult.error("没有该用户！");

    }

    /**
     * 用户信息回显
     *
     * @param userId
     * @return
     * @throws FebsException
     */
    @ApiOperation(value = "用户信息回显", notes = "用户信息回显")
    @GetMapping("/getUserInfo")
    public AjaxResult getUserInfo(@NotBlank(message = "{required}") @RequestParam String userId) throws Exception {

        final SysUserInfoBean sysUserInfoBean = leadsUserInfoService.findById(userId);
        log.info("用户信息回显:{}" + sysUserInfoBean);
        final ResUserInfo resUserInfo = JsonMapper.map(sysUserInfoBean, ResUserInfo.class);
        resUserInfo.setLabels(StringUtils.isEmpty(sysUserInfoBean.getLabels()) ? Collections.emptyList() : JSON.parseArray(sysUserInfoBean.getLabels(), String.class));
        return AjaxResult.success(resUserInfo);

    }

    /**
     * 更新用户信息
     *
     * @param reqSysUserInfo
     * @throws FebsException
     */
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @PutMapping("update/userInfo")
    public AjaxResult updateUserInfo(@RequestBody @Valid ReqSysUserInfo reqSysUserInfo) {

        log.info("更新用户信息:{}" + reqSysUserInfo);
        try {
            final SysUserInfoBean sysUserInfoBean = mapper.map(reqSysUserInfo, SysUserInfoBean.class);
            sysUserInfoBean.setLabels(JSON.toJSONString(reqSysUserInfo.getLabels()));
            sysUserInfoBean.setBirthday(reqSysUserInfo.getBirthday());
            if (CollectionUtils.isNotEmpty(sysUserInfoBean.getResume())) {
                final List<SysUserInfoBean.ResumeBean> resumeBeans = sysUserInfoBean.getResume().stream().map(e -> {
                    final SysUserInfoBean.ResumeBean resumeBean = mapper.map(e, SysUserInfoBean.ResumeBean.class);
                    return resumeBean;
                }).collect(Collectors.toList());
                log.info("更新履历:{}" + resumeBeans);
                sysUserInfoBean.setResume(resumeBeans);
            }
            leadsUserInfoService.updateUserInfo(sysUserInfoBean);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();

    }

    /**
     * 删除履历
     *
     * @param resumeId
     * @return
     */
    @ApiOperation(value = "删除履历", notes = "删除履历")
    @PostMapping("/resume/remove")
    public AjaxResult removeResume(@NotBlank(message = "{required}") @RequestParam String resumeId) {

        leadsUserInfoService.updateResume(resumeId);
        return AjaxResult.success("删除成功");
    }

    /**
     * 用户类型
     *
     * @return
     */
    @ApiOperation(value = "选择用户类型", notes = "选择用户类型")
    @GetMapping("/getUserType")
    public AjaxResult getUserType() {
        final List<Dict> dict = leadsUserService.getUserType();
        final List<ResUserType> resUserTypes = dict.stream().map(e -> {
            final ResUserType resUserType = mapper.map(e, ResUserType.class);
            return resUserType;
        }).collect(Collectors.toList());
        return AjaxResult.success(resUserTypes);

    }

}
