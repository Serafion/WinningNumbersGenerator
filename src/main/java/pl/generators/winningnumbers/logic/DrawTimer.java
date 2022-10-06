package pl.generators.winningnumbers.logic;

import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static pl.generators.winningnumbers.logic.Constants.*;


@Slf4j
class DrawTimer {


    Clock clock;

    public DrawTimer(Clock clock) {
        this.clock = clock;
    }


    public LocalDateTime generateDrawDate() {
        return LocalDateTime.now(clock).getDayOfWeek().equals(DayOfWeek.SATURDAY)
                && LocalDateTime.now(clock).getHour() < HOUR_OF_DRAW ?

                LocalDateTime.now(clock)
                        .toLocalDate()
                        .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
                        .atTime(HOUR_OF_DRAW, MINUTE_OF_DRAW) :

                LocalDateTime.now(clock)
                        .toLocalDate()
                        .with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                        .atTime(HOUR_OF_DRAW, MINUTE_OF_DRAW);
    }

    public boolean itsTimeToMakeADraw(LocalDateTime dateTime) {
        return dateTime.isAfter(generateDrawDate().minusDays(DAYS_BEFORE_DRAW_DATE));
    }


    public LocalDateTime currentTime() {

        return LocalDateTime.now(clock);
    }


}
