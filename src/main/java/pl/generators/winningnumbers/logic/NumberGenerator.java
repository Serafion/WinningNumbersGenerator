package pl.generators.winningnumbers.logic;

import pl.generators.winningnumbers.logic.winningnumbersdto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.generators.winningnumbers.logic.Constants.*;

class NumberGenerator {

    Random random = new Random();

    public NumberGenerator() {

    }

    public WinningNumbersDto generateNumbers(LocalDateTime dateTime) {
        return WinningNumbersMapper.toDto(fetchNumbers(), dateTime);
    }

    private List<Integer> fetchNumbers() {
        return IntStream.generate(() -> random.nextInt(LOW_NUMBER_BOUNDARY,HIGH_NUMBER_BOUNDARY))
                        .distinct()
                        .boxed()
                        .limit(NUMBERS_TO_DRAW)
                        .collect(Collectors.toList());
    }
}
