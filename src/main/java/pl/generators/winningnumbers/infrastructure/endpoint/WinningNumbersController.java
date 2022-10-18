package pl.generators.winningnumbers.infrastructure.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.generators.winningnumbers.logic.Constants;
import pl.generators.winningnumbers.logic.WiningNumbersGeneratorFacade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static pl.generators.winningnumbers.logic.Constants.HOUR_OF_DRAW;

@Slf4j
@RestController
public class WinningNumbersController {

    @Autowired
    WiningNumbersGeneratorFacade winingNumbersGeneratorFacade;

    @RequestMapping(value = "/winningNumbers", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> getNumbers(@RequestParam(name = "date")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                    @RequestParam("pswd") String s) {
        try {
            if (s.equals("abc")) {
                LocalDateTime dateTime = date.atTime(HOUR_OF_DRAW, Constants.MINUTE_OF_DRAW);
                List<Integer> retrievedList = winingNumbersGeneratorFacade.retrieveWonNumbersForDate(dateTime).winningNumbers();
                if(retrievedList.isEmpty()){
                    return ResponseEntity.badRequest().build();
                }
                log.info("returning data for draw date: " + dateTime + " and numbers were: " + retrievedList);
                return ResponseEntity.ok().body(winingNumbersGeneratorFacade.retrieveWonNumbersForDate(dateTime).winningNumbers());
            } else
                log.info("wrong pswd for request");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.info(e.getMessage() + "  and also " + e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }


}
