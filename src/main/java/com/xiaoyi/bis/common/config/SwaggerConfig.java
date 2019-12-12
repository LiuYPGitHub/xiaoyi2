package com.xiaoyi.bis.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 的接口配置
 * 
 * @author 秋山
 */

/**
 * Swagger2 配置类
 * 在与spring boot 集成时，放在与application.java 同级的目录下
 * 通过@Configuration注解，让spring来加载该配置
 * 再通过@EnableSwagger2注解来启动Swagger2
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    /**
     * 创建API应用
     * appinfo()增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例，用来控制那些接口暴露给Swagger来展现
     * 本例采用置顶扫描的包路径来定义指定要建立API的目录
     *
     * @return
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo()).groupName("个人模块")

                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.xiaoyi.bis.user.controller"))
                // 扫描所有
                 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建改API的基本信息（这些基本信息会展示在文档页面中）
     * 访问地址： http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：粒子平台后端API接口文档")
                // 描述
                .description("描述：后端API接口文档")
                // 作者信息
                .contact("秋山")
                // 版本
                .version("版本号:v1.0")
                .build();
    }


    @Bean
    public Docket createLoginRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //
                .apiInfo(apiLoginInfo()).groupName("登录")
                //
                .select()
                //
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //
                .apis(RequestHandlerSelectors.basePackage("com.xiaoyi.bis.system.controller"))
                //
                .paths(PathSelectors.any())
                //
                .build();
    }

    private ApiInfo apiLoginInfo() {
        return new ApiInfoBuilder()
                //
                .title("粒子平台后端-登录API")
                //
                .description("粒子平台后端-登录API登录")
                //
                // .termsOfServiceUrl("http://blog.csdn.net/saytime")
                //
                .version("版本号:v1.0")
                //
                .build();
    }

    @Bean
    public Docket createPingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //
                .apiInfo(apiPingInfo()).groupName("发布管理")
                //
                .select()
                //
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //
                .apis(RequestHandlerSelectors.basePackage("com.xiaoyi.bis.blog.controller"))
                //
                .paths(PathSelectors.any())
                //
                .build();
    }

    private ApiInfo apiPingInfo() {
        return new ApiInfoBuilder()
                //
                .title("粒子平台后端-发布管理模块接口")
                //
                .description("粒子平台后端-发布管理模块接口")
                //
                // .termsOfServiceUrl("http://blog.csdn.net/saytime")
                //
                .version("版本号:v1.0")
                //
                .build();
    }

    @Bean
    public Docket createXiaoYiRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //
                .apiInfo(apiXiaoYiInfo()).groupName("校翼")
                //
                .select()
                //
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //
                .apis(RequestHandlerSelectors.basePackage("com.xiaoyi.bis.xiaoyi.controller"))
                //
                .paths(PathSelectors.any())
                //
                .build();
    }

    private ApiInfo apiXiaoYiInfo() {
        return new ApiInfoBuilder()
                //
                .title("校翼平台后端-直播课程模块接口")
                //
                .description("校翼平台后端-直播课程模块接口")
                //
                // .termsOfServiceUrl("http://blog.csdn.net/saytime")
                //
                .version("版本号:v1.0")
                //
                .build();
    }

    @Bean
    public Docket createHomeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiHome()).groupName("校翼首页")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.xiaoyi.bis.report.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiHome() {
        return new ApiInfoBuilder()
                .title("校翼平台后端-首页报表接口")
                .description("校翼平台后端-首页报表接口")
                .version("版本号:v1.0")
                .build();
    }

}
