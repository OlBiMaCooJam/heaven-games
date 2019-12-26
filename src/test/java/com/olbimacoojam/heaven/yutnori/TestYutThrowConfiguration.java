package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.yut.Yut;
import com.olbimacoojam.heaven.yutnori.yut.YutThrowStrategy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestYutThrowConfiguration {

    @Bean
    @Primary
    public YutThrowStrategy alwaysDoThrowStrategy() {
        return () -> Yut.DO;
    }
}
