package com.olbimacoojam.heaven;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class HeavenApplication {
    private static final String KAKAO = "spring.config.location=classpath:kakao.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(HeavenApplication.class)
                .properties(KAKAO)
                .run(args);
    }

}
