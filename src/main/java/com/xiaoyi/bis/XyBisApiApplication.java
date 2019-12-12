package com.xiaoyi.bis;

import com.xiaoyi.bis.common.utils.SnowflakeIdGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@MapperScan(basePackages = {
        "com.xiaoyi.bis.user.dao",
        "com.xiaoyi.bis.blog.dao",
        "com.xiaoyi.bis.common.dict.dao",
        "com.xiaoyi.bis.xiaoyi.dao",
        "com.xiaoyi.bis.home.dao"
})
@EnableSwagger2
public class XyBisApiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(XyBisApiApplication.class).run(args);
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator(){
        return new SnowflakeIdGenerator(1,1);
    }

}
