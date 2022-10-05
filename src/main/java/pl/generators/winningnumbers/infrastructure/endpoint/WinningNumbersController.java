package pl.generators.winningnumbers.infrastructure.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.generators.winningnumbers.logic.WiningNumbersGeneratorFacade;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class WinningNumbersController {

    @Autowired
    WiningNumbersGeneratorFacade winingNumbersGeneratorFacade;

    @RequestMapping(value = "/get_numbers", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<Integer>> getNumbers(@RequestBody WinningNumbersRequest winningNumbersRequest) {
        try {

            return ResponseEntity.ok().body(winingNumbersGeneratorFacade.retrieveWonNumbersForDate(LocalDateTime.parse(winningNumbersRequest.dateTime)).winningNumbers());
        } catch (Exception e) {
            log.info(e.getMessage() + "  and also " + e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/generate_numbers_manual_mode", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<String> generateNumbers() {
        try {
            if (winingNumbersGeneratorFacade.generateNumbers().isPresent()) {
                return ResponseEntity.ok().body(winingNumbersGeneratorFacade.generateNumbers().get().toString());
            } else {
                return ResponseEntity.ok().body("No numbers generated");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
