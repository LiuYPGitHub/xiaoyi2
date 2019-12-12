package com.xiaoyi.bis.xiaoyi.videoApi;

import com.xiaoyi.bis.xiaoyi.videoApi.bean.APIVideoOneInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2019/12/5
 */
@ToString
@Getter
@Setter
public class GetVideoOneInfoResopnse implements Serializable {

    private static final long serialVersionUID = 3801606670262277254L;

    private APIVideoOneInfo videoOneInfo;

}
