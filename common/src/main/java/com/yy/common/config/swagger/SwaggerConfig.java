package com.yy.common.config.swagger;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    /**
     * 是否开启swagger，正式环境一般是需要关闭的
     */
    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        //添加header参数
        List<Parameter> pars = new ArrayList<>();
        Parameter build = ticketPar.name("Authorization").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                //header中的ticket参数非必填，传空也可以
                .required(false).build();
        //根据每个方法名也知道当前方法在设置什么参数
        pars.add(build);
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否开启
                .enable(swaggerEnabled).select()
                // 扫描的路径包
                //.apis(RequestHandlerSelectors.basePackage("com.yy.*.api"))
                //.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //.paths(Predicates.or(PathSelectors.regex("/.*")))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("支付Demo")
                .description("支付演示接口")
                .contact(new Contact("yyeng", null, null))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("0.0.1")
                .build();
    }
}
