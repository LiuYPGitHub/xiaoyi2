package com.xiaoyi.bis.common.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Description：ioc
 * @Author：kk
 * @Date：2019/8/28 13:21
 */
@Configuration
public class BeanConfiguration {

	@Bean
	public Mapper mapper() {
		return new DozerBeanMapper();
	}

}
