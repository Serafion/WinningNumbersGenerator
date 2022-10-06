package pl.generators.winningnumbers.logic.winningnumbersdto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Document("WinningNumbers")
public record WinningNumbersDto(List<Integer> winningNumbers,
                                @MongoId LocalDateTime drawDate) {
}
