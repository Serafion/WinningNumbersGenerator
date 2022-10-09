package pl.generator.winningnumbers.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.generator.winningnumbers.BaseIntegrationTest;
import pl.generators.winningnumbers.infrastructure.endpoint.WinningNumbersRequest;

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
        WinningNumbersRequest winningNumbersRequest = new WinningNumbersRequest();
        winningNumbersRequest.setDateTime(LocalDateTime.of(2022,02,12,12,00,00).toString());

        Thread.sleep(1000);
        clock.addDays(3);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/get_numbers")
                .content(objectMapper.writeValueAsString(winningNumbersRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        //When
        String stringResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(stringResponse + "WAS THE RESPONSE AT DATE" + LocalDateTime.now(clock));
        List<Integer> list = objectMapper.readValue(stringResponse,List.class);

        //Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(list.size()).isEqualTo(6);

    }

    @Test
    @DisplayName("Should return empty numbers list and valid date format")
    public void should_return_empty_numbers_for_future_date() throws Exception {
        //Given
        WinningNumbersRequest winningNumbersRequest = new WinningNumbersRequest();
        winningNumbersRequest.setDateTime(LocalDateTime.of(2022,02,24,12,00,00).toString());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/get_numbers")
                .content(objectMapper.writeValueAsString(winningNumbersRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        //When
        String stringResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(stringResponse + "WAS THE RESPONSE AT DATE" + LocalDateTime.now(clock));
        List<Integer> list = objectMapper.readValue(stringResponse,List.class);

        //Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return bad request status for invalid request provided")
    public void should_return_bad_request_for_invalid_request() throws Exception {
        //Given

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/get_numbers")
                .content(objectMapper.writeValueAsString("some message"))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        //When & Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 6 numbers for valid date input")
    public void should_process_valid_input_and_return_6_numbers_after_three_rows_of_draws() throws Exception {
        //Given
        WinningNumbersRequest winningNumbersRequest = new WinningNumbersRequest();
        winningNumbersRequest.setDateTime(LocalDateTime.of(2022,02,26,12,00,00).toString());

        Thread.sleep(10000);
        clock.addDays(7);
        Thread.sleep(10000);
        clock.addDays(7);
        Thread.sleep(10000);
        clock.addDays(3);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/get_numbers")
                .content(objectMapper.writeValueAsString(winningNumbersRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        //When
        String stringResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(stringResponse + "WAS THE RESPONSE AT DATE" + LocalDateTime.now(clock));
        List<Integer> list = objectMapper.readValue(stringResponse,List.class);

        //Then
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(list.size()).isEqualTo(6);

    }
}
