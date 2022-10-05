package pl.generators.winningnumbers.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.generators.winningnumbers.MutableClock;
import pl.generators.winningnumbers.logic.repository.WinningNumbersRepository;
import pl.generators.winningnumbers.logic.winningnumbersdto.WinningNumbersDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.generators.winningnumbers.logic.WinningNumbersTestConstants.*;

class WiningNumbersGeneratorFacadeTest {

    //rebuild tests

    WinningNumbersTestConstants constants = new WinningNumbersTestConstants();
    @Test
    @DisplayName("should return six random numbers when valid date")
    void shouldReturnDrawNumbersWhenValidDate() {
        // given
        WinningNumbersRepository repository = new NumberRepositoryForTest();
        Clock clock = Clock.fixed(LocalDateTime.of(2022, 8, 14, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        WiningNumbersGeneratorFacade facade = new WiningNumberGeneratorConfiguration().buildModuleForTests(clock, repository);
        LocalDateTime dateOfDraw = LocalDateTime.of(2022, 8, 13, HOUR_OF_DRAW, MINUTE_OF_DRAW
                , SECOND_OF_DRAW);

        //When
        WinningNumbersDto numbersGenerated = facade.retrieveWonNumbersForDate(dateOfDraw);

        //Then
        assertThat(numbersGenerated.winningNumbers().size()).isEqualTo(6);
        assertThat(numbersGenerated).isEqualTo(repository.findById(dateOfDraw).get());
    }

    @Test
    @DisplayName("Should return six random numbers when going day of draw and return after date of draw")
    void shouldPerformADrawIfDateWasOneDayBeforeDayOfDrawAndProvideNumbersAfterDrawDate() {
        //Given
        WinningNumbersRepository repository = new NumberRepositoryForTest();
        MutableClock clock = new MutableClock(constants.VALID_DATE_OF_DRAW_WHICH_WAS_DRAWN_EARLIER.atZone(ZoneId.systemDefault()));
        WiningNumbersGeneratorFacade facade = new WiningNumberGeneratorConfiguration().buildModuleForTests(clock, repository);


        LocalDateTime dateOfDraw = constants.VALID_DATE_OF_DRAW_TO_BE_DRAWN;

        //When
        facade.retrieveWonNumbersForDate(LocalDateTime.now(clock));
        facade.generateNumbers();
        clock.addDays(8);
        WinningNumbersDto numbersGenerated = facade.retrieveWonNumbersForDate(dateOfDraw);

        //Then
        assertThat(numbersGenerated.winningNumbers().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("Should return zero numbers when asked for draw numbers from future")
    void shouldReturnZeroDrawNumbersDateAfterCurrentDate() {
        //Given
        WinningNumbersRepository repository = new NumberRepositoryForTest();
        Clock clock = Clock.fixed(LocalDateTime.of(2022, 8, 9, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        WiningNumbersGeneratorFacade facade = new WiningNumberGeneratorConfiguration().buildModuleForTests(clock, repository);

        LocalDateTime dateOfDraw = LocalDateTime.of(2022, 8, 13, HOUR_OF_DRAW, MINUTE_OF_DRAW
                , SECOND_OF_DRAW);

        //When
        WinningNumbersDto numbersGenerated = facade.retrieveWonNumbersForDate(dateOfDraw);

        //Then
        assertThat(numbersGenerated.winningNumbers().size()).isEqualTo(0);
    }
}
