package pl.generators.winningnumbers.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.generators.winningnumbers.BaseIntegrationTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class GenerateNumbersIntegrationTest extends BaseIntegrationTest {


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Should return 6 numbers for valid date input")
    public void should_process_valid_input_and_return_6_numbers() throws Exception {
        //Given
        LocalDateTime drawDate = LocalDateTime.of(2022,2,12,12,0,0);

        Thread.sleep(10005);
        clock.addDays(3);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/winningNumbers")
                .param("date",drawDate.toLocalDate().toString())
                .param("pswd","abc")).andReturn();


        //When
        String stringResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(stringResponse + "WAS THE RESPONSE AT DATE" + LocalDateTime.now(clock));
        List<Integer> list = objectMapper.readValue(stringResponse,List.class);

        //Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(list.size()).isEqualTo(6);

    }

    @Test
    @DisplayName("Should return bad request for valid date format but date ")
    public void should_return_bad_request_for_future_date() throws Exception {
        //Given
        LocalDateTime drawDate = LocalDateTime.of(2022,2,24,12,0,0);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/winningNumbers")
                        .param("date",drawDate.toString())
                        .param("pswd","abc")).andReturn();

        //When
        String stringResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(stringResponse + "WAS THE RESPONSE AT DATE" + LocalDateTime.now(clock));

        //Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return bad request status for invalid request provided")
    public void should_return_bad_request_for_invalid_request() throws Exception {
        //Given
        LocalDate someDate = LocalDate.now();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/winningNumbers")
                .param("date",someDate.toString())
                .param("pswd","abc")).andReturn();


        //When & Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 6 numbers for valid date input")
    public void should_process_valid_input_and_return_6_numbers_after_three_rows_of_draws() throws Exception {
        //Given
        LocalDateTime drawDate = LocalDateTime.of(2022,2,26,12,0,0);

        Thread.sleep(10000);
        clock.addDays(7);
        Thread.sleep(10000);
        clock.addDays(7);
        Thread.sleep(10000);
        clock.addDays(3);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/winningNumbers")
                .param("date",drawDate.toLocalDate().toString())
                .param("pswd","abc")).andReturn();


        //When
        String stringResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(stringResponse + "WAS THE RESPONSE AT DATE" + LocalDateTime.now(clock));
        List<Integer> list = objectMapper.readValue(stringResponse,List.class);

        //Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(list.size()).isEqualTo(6);

    }

    @Test
    @DisplayName("Should return bad request status for invalid request provided")
    public void should_return_bad_request_for_invalid_request_provided() throws Exception {
        //Given
        LocalDate someDate = LocalDate.now();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/winningNumbers")
                .param("francis",someDate.toString())
                .param("drake","uncharted")).andReturn();


        //When & Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
