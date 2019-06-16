package com.example.tour.domain.tour;

import com.example.tour.domain.region.ServiceRegionParser;
import com.example.tour.infrastructure.hibernate.program.Program;
import com.example.tour.infrastructure.hibernate.program.ProgramRepository;
import com.example.tour.infrastructure.hibernate.regioncode.ProgramServiceRegion;
import com.example.tour.infrastructure.hibernate.regioncode.ProgramServiceRegionRepository;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.ProviderNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
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
    @Mock
    private ProgramServiceRegionRepository programServiceRegionRepository;
    @Mock
    private ServiceRegionRepository serviceRegionRepository;

    private ServiceRegion serviceRegion;

    @Before
    public void setUp() throws Exception {
        tourInformationService = new TourInformationService(serviceRegionParser,
                                                            programRepository,
                                                            programServiceRegionRepository,
                                                            serviceRegionRepository);
        serviceRegion = ServiceRegion.builder()
                                     .code(1234)
                                     .sidoName("강원도")
                                     .gugunName("속초시")
                                     .build();
    }

    @Test
    public void createTourInformation() {
        TourInformation tourInformation = getDummyTourInformation();

        when(serviceRegionParser.parse(anyString())).thenReturn(Collections.singletonList(serviceRegion));

        tourInformationService.createTourInformation(tourInformation);

        verify(programRepository, times(1)).save(any(Program.class));
        verify(programServiceRegionRepository, times(1)).save(any(ProgramServiceRegion.class));

    }

    @Test
    public void updateTourInformation() {
        Program originProgram = getDummyProgram(serviceRegion);

        TourInformation tourInformation = getDummyTourInformation();

        when(programRepository.findById(anyInt())).thenReturn(Optional.of(originProgram));
        when(serviceRegionParser.parse(anyString())).thenReturn(Collections.singletonList(serviceRegion));

        tourInformationService.updateTourInformation(tourInformation);

        verify(programRepository, times(1)).save(any(Program.class));
    }

    @Test
    public void updateTourInformation__if__service__region__is__modified() {
        ServiceRegion serviceRegion1 = ServiceRegion.builder()
                                                    .code(1)
                                                    .sidoName("강원도")
                                                    .gugunName("속초시")
                                                    .build();
        ServiceRegion serviceRegion2 = ServiceRegion.builder()
                                                    .code(1)
                                                    .sidoName("강원도")
                                                    .gugunName("속초시")
                                                    .build();
        List<ServiceRegion> serviceRegionList = Arrays.asList(serviceRegion1, serviceRegion2);

        Program originProgram = Program.builder()
                                       .id(1)
                                       .name("프로그램1")
                                       .intro("간단한 소개")
                                       .description("설명")
                                       .theme("테마")
                                       .build();

        TourInformation tourInformation = getDummyTourInformation();

        when(programRepository.findById(anyInt())).thenReturn(Optional.of(originProgram));
        when(serviceRegionParser.parse(anyString())).thenReturn(serviceRegionList);

        tourInformationService.updateTourInformation(tourInformation);

        verify(programRepository, times(1)).save(any(Program.class));
        verify(programServiceRegionRepository, times(serviceRegionList.size())).save(any(ProgramServiceRegion.class));
    }

    @Test
    public void getTourInformationListByServiceRegionCode() {
        Program program = getDummyProgram(serviceRegion);
        ProgramServiceRegion programServiceRegion = ProgramServiceRegion.builder()
                                                                        .program(program)
                                                                        .build();

        when(serviceRegionRepository.findFirstByGugunName(anyString())).thenReturn(serviceRegion);
        when(programServiceRegionRepository.findAllByServiceRegion(any(ServiceRegion.class))).thenReturn(Collections.singletonList(
            programServiceRegion));

        tourInformationService.getTourInformationListByServiceRegionCode(serviceRegion.getGugunName());

        verify(programRepository, times(1)).findAllById(anyList());
    }

    private TourInformation getDummyTourInformation() {
        TourInformation tourInformation = new TourInformation();
        tourInformation.setNo(1);
        tourInformation.setName("프로그램");
        tourInformation.setIntro("소개");
        tourInformation.setDescription("설명");
        tourInformation.setTheme("테마");
        tourInformation.setServiceRegionName("강원도양양시");
        return tourInformation;
    }

    private Program getDummyProgram(ServiceRegion serviceRegion) {
        return Program.builder()
                      .id(1)
                      .name("프로그램1")
                      .intro("간단한 소개")
                      .description("설명")
                      .theme("테마")
                      .build();
    }
}