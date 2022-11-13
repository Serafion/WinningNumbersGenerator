package pl.generators.winningnumbers.logic.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.generators.winningnumbers.logic.winningnumbersdto.WinningNumbersDto;

import java.time.LocalDateTime;


@Repository
@Primary
public interface WinningNumbersRepository extends MongoRepository<WinningNumbersDto, LocalDateTime> {

}
