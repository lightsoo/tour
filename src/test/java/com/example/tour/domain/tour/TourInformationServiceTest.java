package com.example.tour.domain.tour;

import com.example.tour.domain.region.ServiceRegionParser;
import com.example.tour.infrastructure.hibernate.program.Program;
import com.example.tour.infrastructure.hibernate.program.ProgramRepository;
import com.example.tour.infrastructure.hibernate.region.ServiceRegion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TourInformationServiceTest {
    private TourInformationService tourInformationService;
    @Mock
    private ServiceRegionParser serviceRegionParser;
    @Mock
    private ProgramRepository programRepository;

    @Before
    public void setUp() throws Exception {
        tourInformationService = new TourInformationService(serviceRegionParser, programRepository);
    }

    @Test
    public void createTourInformation() {
    }

    @Test
    public void updateTourInformation() {
        ServiceRegion serviceRegion = ServiceRegion.builder()
                                                   .code(1234)
                                                   .name("광주광역시")
                                                   .build();

        Program originProgram = Program.builder()
                                       .id(1)
                                       .name("프로그램1")
                                       .intro("간단한 소개")
                                       .description("설명")
                                       .theme("테마")
                                       .serviceRegion(serviceRegion)
                                       .build();

        TourInformation tourInformation = getModifiedTourInformation();

        when(programRepository.findById(anyInt())).thenReturn(Optional.of(originProgram));
        when(serviceRegionParser.parse(anyString())).thenReturn(Collections.singletonList(serviceRegion));

        tourInformationService.updateTourInformation(tourInformation);

        verify(programRepository, times(1)).save(any(Program.class));
    }

    @Test
    public void updateTourInformation__if__service__region__is__modified() {
        ServiceRegion serviceRegion1 = ServiceRegion.builder()
                                                    .code(1)
                                                    .name("광주광역시")
                                                    .build();

        ServiceRegion serviceRegion2 = ServiceRegion.builder()
                                                    .code(12)
                                                    .name("광주광역시서구")
                                                    .build();

        Program originProgram = Program.builder()
                                       .id(1)
                                       .name("프로그램1")
                                       .intro("간단한 소개")
                                       .description("설명")
                                       .theme("테마")
                                       .serviceRegion(serviceRegion1)
                                       .build();

        TourInformation tourInformation = getModifiedTourInformation();

        when(programRepository.findById(anyInt())).thenReturn(Optional.of(originProgram));
        when(serviceRegionParser.parse(anyString())).thenReturn(Arrays.asList(serviceRegion1, serviceRegion2));

        tourInformationService.updateTourInformation(tourInformation);

        verify(programRepository, times(2)).save(any(Program.class));
    }

    @Test
    public void getTourInformation() {

    }

    private TourInformation getModifiedTourInformation() {
        TourInformation tourInformation = new TourInformation();
        tourInformation.setNo(1);
        tourInformation.setName("수정된 프로그램");
        tourInformation.setIntro("수정된 소개");
        tourInformation.setDescription("수정된 설명");
        tourInformation.setTheme("테마");
        tourInformation.setServiceRegionName("광주광역시");
        return tourInformation;
    }

}