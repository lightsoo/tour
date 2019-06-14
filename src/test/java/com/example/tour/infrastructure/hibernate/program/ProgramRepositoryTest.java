package com.example.tour.infrastructure.hibernate.program;

import com.example.tour.infrastructure.hibernate.region.ServiceRegion;
import com.example.tour.infrastructure.hibernate.region.ServiceRegionRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProgramRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private ServiceRegionRepository serviceRegionRepository;
    private Program program1;
    private Program program2;

    @Before
    public void setUp() throws Exception {
        ServiceRegion serviceRegion = ServiceRegion.builder()
                                                   .code("1234")
                                                   .name("광주")
                                                   .build();
        serviceRegionRepository.save(serviceRegion);

        program1 = Program.builder()
                          .name("프로그램1")
                          .intro("간단한 소개")
                          .description("장황한 설명")
                          .theme("테마1")
                          .serviceRegion(serviceRegion)
                          .build();
        program2 = Program.builder()
                          .name("프로그램2")
                          .intro("간단한 소개2")
                          .description("장황한 설명2")
                          .theme("테마2")
                          .serviceRegion(serviceRegion)
                          .build();

    }

    @Test
    public void save__program() {
        programRepository.save(program1);
        programRepository.save(program2);

        Optional<Program> programOptional = programRepository.findById(1);
        programOptional.ifPresent(program -> {
            System.out.println(program);
        });

//        programRepository.findByRegionCode("1234");
    }

    @After
    public void tearDown() throws Exception {
    }
}