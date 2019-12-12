package com.xiaoyi.bis.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoyi.bis.common.annotation.Limit;
import com.xiaoyi.bis.common.authentication.JWTToken;
import com.xiaoyi.bis.common.authentication.JWTUtil;
import com.xiaoyi.bis.common.domain.ActiveUser;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.common.domain.FebsConstant;
import com.xiaoyi.bis.common.properties.FebsProperties;
import com.xiaoyi.bis.common.service.CacheService;
import com.xiaoyi.bis.common.service.RedisService;
import com.xiaoyi.bis.common.utils.*;
import com.xiaoyi.bis.system.dao.LoginLogMapper;
import com.xiaoyi.bis.system.domain.User;
import com.xiaoyi.bis.system.manager.UserManager;
import com.xiaoyi.bis.system.service.LoginLogService;
import com.xiaoyi.bis.system.service.UserService;
import com.xiaoyi.bis.user.bean.LoginUserBean;
import com.xiaoyi.bis.user.domain.LeadsUser;
import com.xiaoyi.bis.user.service.LeadsUserService;
import com.xiaoyi.bis.user.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

@Api(tags = "login", value = "登录")
@Slf4j
@Validated
@Controller
@CrossOrigin
public class LoginController {

    private static final String URL = "http://www.eduleads.com.cn/#/logins";
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserManager userManager;
    @Autowired
    private UserService usrService;
    @Autowired
    private CacheService cacheService;
    private @Autowired
    SmsService smsService;

    @Autowired
    private LeadsUserService leadsUserService;
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private FebsProperties properties;
    @Autowired
    private ObjectMapper mapper;

    public static final String STATUS_LOCK = "2";
    public static final String USERTYPE = "3";

    /**
     * 登录
     *
     * @param telNum
     * @param password
     * @param code
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "登录", notes = "手机号，密码或验证码登录")
    @PostMapping("/login")
    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
    @ResponseBody
    public AjaxResult login(@NotBlank(message = "{required}") @RequestParam String telNum,
                            String password, String code, Boolean remFlag,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("手机号: 密码: code: 是否记住密码:{},{},{},{}", telNum, password, code, remFlag);

        telNum = StringUtils.lowerCase(telNum);

        final String errorMessage = "用户名或密码错误";
        final LeadsUser leadsUser = this.userManager.getSysUser(telNum);

        if (leadsUser == null)
            return AjaxResult.error("用户不存在");

        if (STATUS_LOCK.equals(leadsUser.getUserState()))
            return AjaxResult.error("账号已被锁定,请联系管理员！");

        if (USERTYPE.equals(leadsUser.getUserType())) {
            final String orgState = leadsUserService.getOrgState(leadsUser.getUserInfoId());
            if (STATUS_LOCK.equals(orgState))
                return AjaxResult.error("该机构已禁用,请联系管理员！");
        }

        if (StringUtils.isNotBlank(password)) {
            password = MD5Util.encrypt(password);
            if (!StringUtils.equals(leadsUser.getPassword(), password)) {
                return AjaxResult.error(errorMessage);
            }

        } else if (StringUtils.isNotBlank(code)) {
            String pCode = redisService.get(telNum);
            if (StringUtils.isBlank(pCode))
                return AjaxResult.error("验证码已失效！请重新获取。");
            if (!StringUtils.equals(code, pCode))
                return AjaxResult.error("请输入正确的验证码！");
        } else {
            return AjaxResult.error("请输入正确的密码或验证码！");
        }

        //记住密码
       /* if (remFlag) {
            String loginInfo = telNum + "-" + password;
            CookieUtils.setCookie((HttpServletResponse) response, "loginInfo", loginInfo);
        }*/

        // 更新用户登录时间
        //this.userService.updateLoginTime(username);
        // 保存登录记录
      /*  LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        this.loginLogService.saveLoginLog(loginLog);*/

        String token = FebsUtil.encryptToken(JWTUtil.sign(telNum, properties.getShiro().getJwtSecret()));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        String userId = this.saveTokenToRedis(leadsUser, jwtToken, request);

        final LoginUserBean loginUser = leadsUserService.getLoginUser(leadsUser.getUserId());
        Map<String, Object> userInfo = this.generateUserInfo(jwtToken, loginUser);
        return AjaxResult.success("登录成功", userInfo);
    }

    /**
     * 登录校验用户状态
     *
     * @param telNum
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "登录校验用户状态", notes = "登录校验用户状态")
    @PostMapping("login/check")
    @ResponseBody
    public AjaxResult checkLogin(@NotBlank(message = "{required}") @RequestParam String telNum) {
        final LeadsUser byTel = leadsUserService.findByTel(telNum);
        if (byTel == null) {
            return AjaxResult.error("该账号尚未注册！请先注册");
        }
        return AjaxResult.success("成功");
    }


    @GetMapping("index/{username}")
    @ResponseBody
    public AjaxResult index(@NotBlank(message = "{required}") @PathVariable String username) {
        Map<String, Object> data = new HashMap<>();
        // 获取系统访问记录
        Long totalVisitCount = loginLogMapper.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = loginLogMapper.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = loginLogMapper.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = loginLogMapper.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = loginLogMapper.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return AjaxResult.success("登录成功", data);

    }

    @RequiresPermissions("user:online")
    @GetMapping("online")
    @ResponseBody
    public AjaxResult userOnline(String username) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        List<ActiveUser> activeUsers = new ArrayList<>();
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            activeUser.setToken(null);
            if (StringUtils.isNotBlank(username)) {
                if (StringUtils.equalsIgnoreCase(username, activeUser.getUsername()))
                    activeUsers.add(activeUser);
            } else {
                activeUsers.add(activeUser);
            }
        }
        return AjaxResult.success("成功", activeUsers);

    }

    /**
     * 踢掉账号
     *
     * @param userId
     * @throws Exception
     */
    @DeleteMapping("kickout/{userId}")
    @RequiresPermissions("user:kickout")
    @ResponseBody
    public void kickout(@NotBlank(message = "{required}") @PathVariable String userId) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        ActiveUser kickoutUser = null;
        String kickoutUserString = "";
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            if (StringUtils.equals(activeUser.getId(), userId)) {
                kickoutUser = activeUser;
                kickoutUserString = userOnlineString;
            }
        }
        if (kickoutUser != null && StringUtils.isNotBlank(kickoutUserString)) {
            // 删除 zset中的记录
            redisService.zrem(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, kickoutUserString);
            // 删除对应的 token缓存
            redisService.del(FebsConstant.TOKEN_CACHE_PREFIX + kickoutUser.getToken() + "." + kickoutUser.getIp());
        }
    }

    /**
     * 退出登录
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "退出登录参数 userId", notes = "退出登录")
    @PostMapping("logout")
    @ResponseBody
    public AjaxResult logout(@NotBlank(message = "{required}") @RequestParam("userId") String userId) throws Exception {
        log.info("退出登录参数:{}" + userId);
      /*  if (StringUtils.isEmpty(reqUser.getUserId())) {
            return AjaxResult.error("缺少参数");
        }*/
        this.kickout(userId);
        return AjaxResult.success("退出成功");

    }

    /**
     * 模拟登录
     *
     * @param telNum
     * @param token
     * @param url
     * @param request
     * @return
     */
    @ApiOperation(value = "模拟登录", notes = "模拟登录")
    @GetMapping(value = "sso/{telNum}/{token}")
    public String sso(@PathVariable String telNum, @PathVariable String token,
                      HttpServletRequest request) {

        log.info("模拟登录:" + telNum + "," + token);
        if (token != null) {
            final String decryptToken = FebsUtil.decryptToken(token);
            String username = JWTUtil.getUsername(decryptToken);
            if (StringUtils.isBlank(username))
                throw new AuthenticationException("token校验不通过");
            if (!JWTUtil.verify(decryptToken, username, properties.getShiro().getJwtSecret()))
                throw new AuthenticationException("token校验不通过");
            final LeadsUser leadsUser = this.userManager.getSysUser(telNum);

            if (leadsUser != null) {
                if (StringUtils.isNotBlank(leadsUser.getPassword())) {
                    String redirectUrl = URL + "?iqMbSlAj04aPkfh9=" + telNum + "&c8PwVBJBhjGORsGK=" + leadsUser.getPassword() + "&bBjIStYb3WjFwoOD=" + token;
                    return "redirect:" + redirectUrl;
                }
            }

        }
        return "403";

    }

    /**
     * 模拟登录
     *
     * @param telNum
     * @param password
     * @param token
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "登录", notes = "手机号，密码或验证码登录")
    @PostMapping("/ssoLogin")
    @Limit(key = "ssoLogin", period = 60, count = 20, name = "登录接口", prefix = "limit")
    @ResponseBody
    public AjaxResult ssoLogin(@NotBlank(message = "{required}") @RequestParam("iqMbSlAj04aPkfh9") String telNum,
                               @NotBlank(message = "{required}") @RequestParam("c8PwVBJBhjGORsGK") String password,
                               @NotBlank(message = "{required}") @RequestParam("bBjIStYb3WjFwoOD") String token,
                               HttpServletRequest request) throws Exception {

        log.info("手机号: 密码: {},{},{}", telNum, password, token);

        final LeadsUser leadsUser = this.userManager.getSysUser(telNum);
        if (leadsUser == null)
            return AjaxResult.error("用户不存在");
        if (!StringUtils.equals(leadsUser.getPassword(), password))
            return AjaxResult.error("密码错误");
        if (STATUS_LOCK.equals(leadsUser.getUserState()))
            return AjaxResult.error("账号已被锁定,请联系管理员！");

        final LoginUserBean loginUser = leadsUserService.getLoginUser(leadsUser.getUserId());

        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);
        String userId = this.saveTokenToRedis(leadsUser, jwtToken, request);
        Map<String, Object> userInfo = this.generateUserInfo(jwtToken, loginUser);
        return AjaxResult.success("登录成功", userInfo);
    }


    /**
     * 注册
     *
     * @param telNum
     * @param code
     * @throws Exception
     */
    @ApiOperation(value = "注册", notes = "手机号验证码")
    @PostMapping("/register")
    @ResponseBody
    public AjaxResult register(@NotBlank(message = "{required}") @RequestParam String telNum,
                               @NotBlank(message = "{required}") @RequestParam String code,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        final LeadsUser byTel = leadsUserService.findByTel(telNum);
        if (byTel != null) {
            return AjaxResult.error("账号已存在！");
        }
        String pCode = redisService.get(telNum + "register");
        if (StringUtils.isEmpty(pCode))
            return AjaxResult.error("验证码已失效！请重新获取！");

        if (!StringUtils.equals(code, pCode))
            return AjaxResult.error("请输入正确的验证码！");

        final LeadsUser leadsUser = this.leadsUserService.register(telNum);

        String token = FebsUtil.encryptToken(JWTUtil.sign(telNum, properties.getShiro().getJwtSecret()));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        String userId = this.saveTokenToRedis(leadsUser, jwtToken, request);

        final LoginUserBean loginUser = leadsUserService.getLoginUser(leadsUser.getUserId());
        loginUser.setRealName(loginUser.getRealName() == null ? "姓名" : loginUser.getRealName());
        Map<String, Object> userInfo = this.generateUserInfo(jwtToken, loginUser);
        return AjaxResult.success("注册成功", userInfo);
    }

    /**
     * 注册校验用户状态
     *
     * @param telNum
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "注册校验用户状态", notes = "注册校验用户状态")
    @PostMapping("register/check")
    @ResponseBody
    public AjaxResult checkRegister(@NotBlank(message = "{required}") @RequestParam String telNum) throws Exception {
        final LeadsUser byTel = leadsUserService.findByTel(telNum);
        if (byTel != null) {
            return AjaxResult.error("该账号已注册！请登录");
        }
        return AjaxResult.success("成功");
    }


    /**
     * 注册时获取验证码
     *
     * @param telNum
     * @return
     */
    @Deprecated
    @ApiOperation(value = "注册时获取验证码", notes = "注册时获取验证码")
    @PostMapping("getRegisterCode")
    @ResponseBody
    public AjaxResult getRegisterCode(@NotBlank(message = "{required}") @RequestParam String telNum) {

        log.info("注册时获取验证码入参:{}" + telNum);
        int smsCode = (int) ((Math.random() * 9 + 1) * 100000);
        try {
            final String s = redisService.get(telNum);
            if (StringUtils.isEmpty(s)) {
                //SmsUtils.sendCode(telNum, smsCode);
                final String code = smsService.sendSms(telNum, smsCode);
                redisService.set(telNum + "register", String.valueOf(smsCode), (long) 1800000);
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
            return AjaxResult.success("发送成功", StringUtils.isEmpty(s) ? smsCode : s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("没有该用户！");
    }

    /**
     * 生成前端需要的用户信息，包括：
     * 1. token
     * 2. Vue Router
     * 3. 用户角色
     * 4. 用户权限
     * 5. 前端系统个性化配置信息
     *
     * @param token token
     * @param user  用户信息
     * @return UserInfo
     */
    private Map<String, Object> generateUserInfo(JWTToken token, LoginUserBean user) {
        log.info("生成前端需要的用户信息:{},{}", token, user);
        String telName = user.getTelNum();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("exipreTime", token.getExipreAt());
/*
        Set<String> roles = this.userManager.getUserRoles(username);
        userInfo.put("roles", roles);

        Set<String> permissions = this.userManager.getUserPermissions(username);
        userInfo.put("permissions", permissions);

        UserConfig userConfig = this.userManager.getUserConfig(String.valueOf(user.getUserId()));
        userInfo.put("config", userConfig);

        user.setPassword("it's a secret");*/
        userInfo.put("user", user);
        return userInfo;
    }

    private String saveTokenToRedis(LeadsUser user, JWTToken token, HttpServletRequest request) throws Exception {
        String ip = IPUtil.getIpAddr(request);

        // 构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(user.getTelNum());
        activeUser.setIp(ip);
        activeUser.setToken(token.getToken());
        activeUser.setLoginAddress(AddressUtil.getCityInfo(ip));

        // zset 存储登录用户，score 为过期时间戳
        this.redisService.zadd(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(token.getExipreAt()), mapper.writeValueAsString(activeUser));
        // redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
        this.redisService.set(FebsConstant.TOKEN_CACHE_PREFIX + token.getToken() + StringPool.DOT + ip, token.getToken(), properties.getShiro().getJwtTimeOut() * 1000);

        return activeUser.getId();
    }

}
