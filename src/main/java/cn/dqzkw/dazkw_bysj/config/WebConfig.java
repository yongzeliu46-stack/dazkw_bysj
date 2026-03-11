package cn.dqzkw.dazkw_bysj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 /uploads/** 映射到本地磁盘路径
        String path = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + path);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域请求的路径
        registry.addMapping("/**")
                // 允许跨域请求的源
                .allowedOriginPatterns("*")
                // 允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 是否允许证书
                .allowCredentials(true)
                // 跨域允许时间
                .maxAge(3600);
    }
}
