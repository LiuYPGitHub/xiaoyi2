package com.xiaoyi.bis.user.controller;

import com.xiaoyi.bis.common.controller.BaseController;
import com.xiaoyi.bis.common.dict.domain.Dict;
import com.xiaoyi.bis.common.domain.AjaxResult;
import com.xiaoyi.bis.user.bean.CooperationBean;
import com.xiaoyi.bis.user.bean.ResCooType;
import com.xiaoyi.bis.user.bean.ReqCooperation;
import com.xiaoyi.bis.user.service.CooperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description：合作
 * @Author：kk
 * @Date：2019/8/30 11:24
 */
@Api(tags = "cooperation", value = "合作")
@Slf4j
@Validated
@RestController
@RequestMapping("/web")
public class CooperationController extends BaseController {

    private @Autowired
    CooperationService cooperationService;
    private @Autowired
    Mapper mapper;

    /**
     * 合作数据回显
     *
     * @param orgId
     * @param cooperationId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "合作数据回显", notes = "合作数据回显")
    @GetMapping("cooperation/show")
    public AjaxResult getCooperation(@NotBlank(message = "{required}") @RequestParam String orgId,
                                     @RequestParam(value = "cooperationId", required = false) String cooperationId) throws Exception {

        final CooperationBean cooperationInfo = cooperationService.getCooperationInfo(orgId, cooperationId);
        return AjaxResult.success(cooperationInfo);
    }

    /**
     * 合作
     *
     * @param reqCooperation
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "合作", notes = "合作")
    @PostMapping("cooperation/create")
    public AjaxResult createCooperation(@RequestBody @Valid ReqCooperation reqCooperation) throws Exception {

        log.info("合作入参 :{}", reqCooperation);
        final CooperationBean cooperationBean = mapper.map(reqCooperation, CooperationBean.class);
        cooperationService.saveCooperation(cooperationBean);
        return AjaxResult.success();
    }


    /**
     * 用户类型
     *
     * @return
     */
    @ApiOperation(value = "选择用户类型", notes = "选择用户类型")
    @GetMapping("/getCooType")
    public AjaxResult getCooType() {

        final List<Dict> dict = cooperationService.getCooType();
        final List<ResCooType> collect = dict.stream().map(e -> {
            final ResCooType resCooType = mapper.map(e, ResCooType.class);
            return resCooType;
        }).collect(Collectors.toList());
        return AjaxResult.success(collect);

    }

}
