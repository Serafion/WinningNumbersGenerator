package pl.generators.winningnumbers.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import pl.generators.winningnumbers.logic.repository.WinningNumbersRepository;
import pl.generators.winningnumbers.logic.winningnumbersdto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
public class WiningNumbersGeneratorFacade {
    WinningNumbersRepository numRepo;
    DrawTimer timer;
    NumberGenerator generator;

    public WiningNumbersGeneratorFacade(WinningNumbersRepository numRepo, DrawTimer timer, NumberGenerator generator) {
        this.numRepo = numRepo;
        this.timer = timer;
        this.generator = generator;
    }

    public WinningNumbersDto retrieveWonNumbersForDate(LocalDateTime dateTime) {
        if (timer.currentTime().isAfter(dateTime))
            return numRepo.findById(dateTime).orElseThrow(() -> new ResourceNotFoundException(dateTime.toString()));
        throw new DateBeforeDateOfDrawException(dateTime.toString());
    }

    //Refactor generate numbers. It's an internal process and don't require returning optional;
    @Scheduled(cron = "${app.cron}")
    public Optional<WinningNumbersDto> generateNumbers() {
        LocalDateTime dateOfDraw = timer.generateDrawDate();
        log.info(dateOfDraw.toString()+" has numbers data:" + numRepo.existsById(dateOfDraw));
        log.info("It's time to make a draw: " + timer.itsTimeToMakeADraw(dateOfDraw));
        if (!numRepo.existsById(dateOfDraw) && timer.itsTimeToMakeADraw(dateOfDraw)) {
            WinningNumbersDto result = generator.generateNumbers(dateOfDraw);
            log.info("Generated Numbers" + result.winningNumbers()+ "for date: "+dateOfDraw);
            numRepo.save(result);
            return Optional.of(result);
        }
        return Optional.empty();
    }

}
