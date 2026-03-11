package cn.dqzkw.dazkw_bysj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.dqzkw.dazkw_bysj.mapper")
public class DazkwBysjApplication {

    public static void main(String[] args) {
        SpringApplication.run(DazkwBysjApplication.class, args);
    }

}
