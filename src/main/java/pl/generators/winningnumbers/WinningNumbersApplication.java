package pl.generators.winningnumbers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "pl.generators.winningnumbers")
@EnableScheduling()
public class WinningNumbersApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinningNumbersApplication.class, args);
	}

}
