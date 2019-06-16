package com.example.tour.infrastructure.hibernate.regioncode;

import com.example.tour.infrastructure.hibernate.program.Program;
import com.example.tour.infrastructure.hibernate.program.ProgramRepository;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProgramRegionCodeRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private ServiceRegionRepository serviceRegionRepository;
    @Autowired
    private ProgramServiceRegionRepository programServiceRegionRepository;

    private Program program1;
    private Program program2;
    private ServiceRegion serviceRegion;

    @Before
    public void setUp() throws Exception {
        String serviceRegionName = "광주광역시";
        serviceRegion = serviceRegionRepository.findFirstByGugunName(serviceRegionName);

        program1 = Program.builder()
                          .name("프로그램1")
                          .intro("간단한 소개")
                          .description("설명")
                          .serviceRegionName(serviceRegionName)
                          .theme("테마1")
                          .build();

        programRepository.save(program1);

        programServiceRegionRepository.save(
            ProgramServiceRegion.builder()
                                .serviceRegion(serviceRegion)
                                .program(program1)
                                .build()
        );

        program2 = Program.builder()
                          .name("프로그램2")
                          .intro("간단한 소개2")
                          .description("설명2")
                          .serviceRegionName(serviceRegionName)
                          .theme("테마2")
                          .build();

        programRepository.save(program2);

        programServiceRegionRepository.save(
            ProgramServiceRegion.builder()
                                .serviceRegion(serviceRegion)
                                .program(program2)
                                .build()
        );
    }

    @Test
    public void findAllByCode() {
        List<ProgramServiceRegion> result = programServiceRegionRepository.findAllByServiceRegion(serviceRegion);

        assertThat(result.size()).isEqualTo(2);
    }

}