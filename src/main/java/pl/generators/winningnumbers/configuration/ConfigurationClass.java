package pl.generators.winningnumbers.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ConfigurationClass {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
