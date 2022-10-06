package pl.generators.winningnumbers.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.generators.winningnumbers.logic.repository.WinningNumbersRepository;

import java.time.Clock;

@Configuration
public class WiningNumberGeneratorConfiguration {

    @Bean
    @Primary
    public WiningNumbersGeneratorFacade winingNumbersGeneratorFacade(WinningNumbersRepository winningNumbersRepository, Clock clock) {
        return buildDefaultModule(clock, winningNumbersRepository);
    }


    public WiningNumbersGeneratorFacade buildDefaultModule(Clock clock, WinningNumbersRepository repository) {
        DrawTimer timer = new DrawTimer(clock);
        NumberGenerator generator = new NumberGenerator();
        return new WiningNumbersGeneratorFacade(repository, timer, generator);
    }

    public WiningNumbersGeneratorFacade buildModuleForTests(Clock clock, WinningNumbersRepository repository) {
        return buildDefaultModule(clock, repository);
    }
}
