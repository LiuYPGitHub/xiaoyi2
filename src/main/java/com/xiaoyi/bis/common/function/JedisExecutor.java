package com.xiaoyi.bis.common.function;

import com.xiaoyi.bis.common.exception.RedisConnectException;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
