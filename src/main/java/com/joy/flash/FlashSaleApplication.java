package com.joy.flash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created by SongLiang on 2019-09-09
 */
@SpringBootApplication
public class FlashSaleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FlashSaleApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FlashSaleApplication.class);
    }

}
