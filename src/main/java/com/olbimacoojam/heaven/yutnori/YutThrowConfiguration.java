package com.olbimacoojam.heaven.yutnori;

import com.olbimacoojam.heaven.yutnori.yut.RandomYutThrowStrategy;
import com.olbimacoojam.heaven.yutnori.yut.YutThrowStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YutThrowConfiguration {

    @Bean
    public YutThrowStrategy randomYutThrowStrategy() {
        return new RandomYutThrowStrategy();
    }
}
