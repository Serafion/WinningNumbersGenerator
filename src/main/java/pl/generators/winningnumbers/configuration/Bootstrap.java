package pl.generators.winningnumbers.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.generators.winningnumbers.logic.repository.WinningNumbersRepository;
import pl.generators.winningnumbers.logic.winningnumbersdto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@Profile("tests")
public class Bootstrap implements CommandLineRunner {

    @Autowired
    WinningNumbersRepository winningNumbersRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Boostrap data loading");
        WinningNumbersDto dto = new WinningNumbersDto(List.of(1,2,3,4,5,6), LocalDateTime.of(2022,02,12,12,0));
        winningNumbersRepository.save(dto);
        log.info(String.valueOf(winningNumbersRepository.findById(dto.drawDate()).get()));
    }
}
