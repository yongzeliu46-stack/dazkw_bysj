package cn.dqzkw.dazkw_bysj.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("大庆市中考网 API")
                        .version("1.0.0")
                        .description("本文档展示了大庆中考网设计系统的所有 API 接口及其说明。"));
    }
}
