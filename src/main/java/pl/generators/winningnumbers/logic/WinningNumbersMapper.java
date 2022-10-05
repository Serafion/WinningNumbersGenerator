package pl.generators.winningnumbers.logic;

import pl.generators.winningnumbers.logic.winningnumbersdto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;

class WinningNumbersMapper {
    static WinningNumbersDto toDto(List<Integer> list, LocalDateTime dateTime) {
        return new WinningNumbersDto(list, dateTime);
    }
}
