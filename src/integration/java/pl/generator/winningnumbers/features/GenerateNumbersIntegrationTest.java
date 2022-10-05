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

//    @Test
//    @DisplayName("Should return error status for invalid request")
//    public void should_not_process_invalid_input_and_return_422_error() throws Exception {
//        //Given
//        InputNumbersRequest inputNumbersRequest = new InputNumbersRequest();
//        inputNumbersRequest.setNumbers("1,2,3,4,5,101");
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/process")
//                .content(objectMapper.writeValueAsString(inputNumbersRequest))
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//
//        // When && Then
//        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
//    }
//
//    @Test
//    @DisplayName("Should return won six numbers")
//    public void should_return_won_6_numbers_if_valid_ticket_won() throws Exception {
//        //Given
//        //Input numbers part
//        InputNumbersRequest inputNumbersRequest = new InputNumbersRequest();
//        inputNumbersRequest.setNumbers("1,12,23,34,45,66");
//        MvcResult inputNumbersRequestMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/process")
//                .content(objectMapper.writeValueAsString(inputNumbersRequest))
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//
//        //When
//        String stringResponse = inputNumbersRequestMvcResult.getResponse().getContentAsString();
//        NumberReceiverResultDto result = objectMapper.readValue(stringResponse, NumberReceiverResultDto.class);
//
//        //Then
//        assertThat(inputNumbersRequestMvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(result.message()).isEqualTo("CORRECT_MESSAGE");
//        assertThat(result.userNumbers()).isEqualTo(List.of(1, 12, 23, 34, 45, 66));
//        assertThat(result.uniqueLotteryId().isPresent()).isTrue();
//        assertThat(result.dateOfDraw().get().toString()).isEqualTo(LocalDateTime.of(2022, 02, 12, 12, 00, 00).toString());
//
//        //Given
//        winningNumbersRepository.save(new WinningNumbersDto(result.userNumbers(), result.dateOfDraw().get()));
//        clock.addDays(7);
//        ResultRequest resultRequest = new ResultRequest();
//        resultRequest.setUuid(result.uniqueLotteryId().get().toString());
//        MvcResult resultAnnouncerResult = mockMvc.perform(MockMvcRequestBuilders.get("/get_results")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(resultRequest)))
//                .andReturn();
//
//        //When
//        String result_response = resultAnnouncerResult.getResponse().getContentAsString();
//
//        //Then
//        assertThat(resultAnnouncerResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(result_response).isEqualTo("You've hit: 6 numbers");
//    }
//
//    @Test
//    @DisplayName("Should return won 5 numbers")
//    public void should_return_won_5_numbers_if_valid_ticket_won() throws Exception {
//        //Given
//        InputNumbersRequest inputNumbersRequest = new InputNumbersRequest();
//        inputNumbersRequest.setNumbers("1,2,3,4,5,99");
//        MvcResult inputNumbersRequestMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/process")
//                .content(objectMapper.writeValueAsString(inputNumbersRequest))
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//
//        //When
//        String stringResponse = inputNumbersRequestMvcResult.getResponse().getContentAsString();
//        NumberReceiverResultDto result = objectMapper.readValue(stringResponse, NumberReceiverResultDto.class);
//
//        //Then
//        assertThat(inputNumbersRequestMvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(result.message()).isEqualTo("CORRECT_MESSAGE");
//        assertThat(result.userNumbers()).isEqualTo(List.of(1, 2, 3, 4, 5, 99));
//        assertThat(result.uniqueLotteryId().isPresent()).isTrue();
//        assertThat(result.dateOfDraw().get().toString()).isEqualTo(LocalDateTime.of(2022, 02, 12, 12, 00, 00).toString());
//
//        //Given
//        winningNumbersRepository.save(new WinningNumbersDto(List.of(1, 2, 3, 4, 5, 6), result.dateOfDraw().get()));
//        clock.addDays(7);
//        ResultRequest resultRequest = new ResultRequest();
//        resultRequest.setUuid(result.uniqueLotteryId().get().toString());
//        MvcResult resultAnnouncerResult = mockMvc.perform(MockMvcRequestBuilders.get("/get_results")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(resultRequest)))
//                .andReturn();
//
//        //When
//        String result_response = resultAnnouncerResult.getResponse().getContentAsString();
//
//        //Then
//        assertThat(resultAnnouncerResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(result_response).isEqualTo("You've hit: 5 numbers");
//    }
//
//    @Test
//    @DisplayName("Should return loosing status for valid dto but with not winning ticket")
//    public void should_return_loosing_message_for_loosing_ticket() throws Exception {
//        //Given
//        InputNumbersRequest inputNumbersRequest = new InputNumbersRequest();
//        inputNumbersRequest.setNumbers("1,2,96,97,98,99");
//        MvcResult inputNumbersRequestMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/process")
//                .content(objectMapper.writeValueAsString(inputNumbersRequest))
//                .contentType(MediaType.APPLICATION_JSON)).andReturn();
//
//        //When
//        String stringResponse = inputNumbersRequestMvcResult.getResponse().getContentAsString();
//        NumberReceiverResultDto result = objectMapper.readValue(stringResponse, NumberReceiverResultDto.class);
//
//        //Then
//        assertThat(inputNumbersRequestMvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(result.message()).isEqualTo("CORRECT_MESSAGE");
//        assertThat(result.userNumbers()).isEqualTo(List.of(1, 2, 96, 97, 98, 99));
//        assertThat(result.uniqueLotteryId().isPresent()).isTrue();
//        assertThat(result.dateOfDraw().get().toString()).isEqualTo(LocalDateTime.of(2022, 02, 12, 12, 00, 00).toString());
//
//
//        //Given
//        winningNumbersRepository.save(new WinningNumbersDto(List.of(1, 2, 3, 4, 5, 6), result.dateOfDraw().get()));
//        clock.addDays(7);
//        ResultRequest resultRequest = new ResultRequest();
//        resultRequest.setUuid(result.uniqueLotteryId().get().toString());
//        MvcResult resultAnnouncerResult = mockMvc.perform(MockMvcRequestBuilders.get("/get_results")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(resultRequest)))
//                .andReturn();
//
//        //When
//        String result_response = resultAnnouncerResult.getResponse().getContentAsString();
//
//        //Then
//        assertThat(resultAnnouncerResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(result_response).isEqualTo("sorry it's a loosing game");
//    }
//
//    @Test
//    @DisplayName("Should return error message if invalid uuid provided")
//    public void should_return_http_error_status_for_invalid_uuid_provided() throws Exception {
//        //Given
//        ResultRequest resultRequest = new ResultRequest();
//        resultRequest.setUuid("It's obviously not an UUID of any kind as i may see it clearly here");
//        MvcResult resultAnnouncerResult = mockMvc.perform(MockMvcRequestBuilders.get("/get_results")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(resultRequest)))
//                .andReturn();
//
//        //When
//        MockHttpServletResponse response = resultAnnouncerResult.getResponse();
//
//        //Then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//        assertThat(response.getContentAsString()).isEqualTo("Invalid UUID data provided");
//    }
}
