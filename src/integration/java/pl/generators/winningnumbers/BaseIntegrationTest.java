package pl.generators.winningnumbers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.generators.winningnumbers.logic.WiningNumbersGeneratorFacade;

import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootTest(
        classes = WinningNumbersApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "application.environment=integration"
)
@Import(TestConfig.class)
@Testcontainers
public class BaseIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    public MutableClock clock;
//    @SpyBean
    @Autowired
    public WiningNumbersGeneratorFacade winingNumbersGeneratorFacade;

    @BeforeEach
    void reset() {
        //reset clock for tests
        clock.setToday(LocalDateTime.of(2022, 2, 12, 10, 11, 0).atZone(ZoneId.systemDefault()));
    }

    static {
        mongoDBContainer.start();
        System.setProperty("DB_PORT", String.valueOf(mongoDBContainer.getFirstMappedPort()));
    }
}
