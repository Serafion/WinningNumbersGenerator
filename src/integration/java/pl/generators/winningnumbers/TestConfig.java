package pl.generators.winningnumbers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import pl.generators.winningnumbers.MutableClock;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;


@TestConfiguration
public class TestConfig {


    @Bean
    @Primary
    public Clock clockMock() {
        return new MutableClock(LocalDateTime.of(2022, 2, 11, 10, 11, 0).atZone(ZoneId.systemDefault()));
    }


}
