package pl.generators.winningnumbers.logic;

import pl.generators.winningnumbers.logic.winningnumbersdto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WinningNumbersTestConstants {
    public static final int HOUR_OF_DRAW = 12;
    public static final int MINUTE_OF_DRAW = 0;
    public static final int SECOND_OF_DRAW = 0;
    public final LocalDateTime VALID_DATE_OF_DRAW_WHICH_WAS_DRAWN_EARLIER = LocalDateTime.of(2022, 8, 13, HOUR_OF_DRAW, MINUTE_OF_DRAW
            , SECOND_OF_DRAW);
    public final LocalDateTime VALID_DATE_OF_DRAW_TO_BE_DRAWN = LocalDateTime.of(2022, 8, 20, HOUR_OF_DRAW, MINUTE_OF_DRAW
            , SECOND_OF_DRAW);
    public final LocalDateTime INVALID_DATE_OF_DRAW = LocalDateTime.of(2022, 8, 15, HOUR_OF_DRAW, MINUTE_OF_DRAW
            , SECOND_OF_DRAW);
    public final HashMap<LocalDateTime, WinningNumbersDto> map;
    private final List<LocalDateTime> drawDates;
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);

    public WinningNumbersTestConstants() {
        this.map = new HashMap<>();
        map.put(VALID_DATE_OF_DRAW_WHICH_WAS_DRAWN_EARLIER, WinningNumbersMapper.toDto(list, VALID_DATE_OF_DRAW_WHICH_WAS_DRAWN_EARLIER));
        drawDates = new ArrayList<>();
        drawDates.add(VALID_DATE_OF_DRAW_WHICH_WAS_DRAWN_EARLIER);

    }

    public HashMap<LocalDateTime, WinningNumbersDto> getMap() {
        return map;
    }
}
