package pl.generators.winningnumbers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.generators.winningnumbers.logic.repository.WinningNumbersRepository;
import pl.generators.winningnumbers.logic.winningnumbersdto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    WinningNumbersRepository winningNumbersRepository;

    @Override
    public void run(String... args) throws Exception {
        WinningNumbersDto dto = new WinningNumbersDto(List.of(1,2,3,4,5,6), LocalDateTime.of(2022,10,1,12,0));
        winningNumbersRepository.save(dto);
    }
}
