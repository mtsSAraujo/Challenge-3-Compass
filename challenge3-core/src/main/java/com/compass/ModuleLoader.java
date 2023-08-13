package com.compass;

import com.compass.util.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableAsync
@EntityScan
@ComponentScan
@EnableJpaRepositories
@Configuration
@PropertySources({
        @PropertySource(
                value = "classpath:application_core.yml",
                factory = YamlPropertySourceFactory.class)
})
public class ModuleLoader {
}
