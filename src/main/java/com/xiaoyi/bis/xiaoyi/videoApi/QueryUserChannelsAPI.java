package com.xiaoyi.bis.xiaoyi.videoApi;

import java.security.NoSuchAlgorithmException;

/**
 * 查询用户所属的所有频道
 * @author CJ
 * @date 2019/10/22
 */
public interface QueryUserChannelsAPI{

    QueryUserChannelsResponse process(QueryUserChannelsRequest request) throws NoSuchAlgorithmException;

    QueryUserChannelsResponse processJson(String json);

}
