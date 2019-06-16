package com.example.tour.api.v1;

import com.example.tour.api.v1.request.TourInformationRequestDTO;
import com.example.tour.domain.tour.TourInformation;
import com.example.tour.domain.tour.TourInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TourController.class)
public class TourControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TourInformationService tourInformationService;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void success__createTourInformation() throws Exception {
        TourInformationRequestDTO requestDTO = TourInformationRequestDTO.builder()
                                                                        .name("name")
                                                                        .theme("테마")
                                                                        .serviceRegionName("광주광역시")
                                                                        .build();

        mvc.perform(post("/tour/v1/program")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        )
           .andDo(print())
           .andExpect(status().isCreated());

        verify(tourInformationService, times(1)).createTourInformation(any(TourInformation.class));

    }

    @Test
    public void fail__createTourInformation__if__require__parameter__is__empty() throws Exception {
        TourInformationRequestDTO requestDTO = TourInformationRequestDTO.builder()
                                                                        .theme("테마")
                                                                        .serviceRegionName("광주광역시")
                                                                        .build();

        mvc.perform(post("/tour/v1/program")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        )
           .andDo(print())
           .andExpect(status().isBadRequest());
    }

    @Test
    public void success__modifyTourInformation() throws Exception {
        TourInformationRequestDTO requestDTO = TourInformationRequestDTO.builder()
                                                                        .no(1)
                                                                        .name("name")
                                                                        .theme("테마")
                                                                        .serviceRegionName("광주광역시")
                                                                        .build();

        mvc.perform(put("/tour/v1/program")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
        )
           .andDo(print())
           .andExpect(status().isOk());

        verify(tourInformationService, times(1)).updateTourInformation(any(TourInformation.class));

    }

    @Test
    public void success__getTourInformation() {
    }
}