package com.example.tour.infrastructure.hibernate.program;

import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegionRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProgramRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private ServiceRegionRepository serviceRegionRepository;
    private Program program1;
    private Program program2;
    private ServiceRegion serviceRegion;

    @Before
    public void setUp() throws Exception {
        serviceRegion = ServiceRegion.builder()
                                     .code(1234)
                                     .name("광주")
                                     .build();
        serviceRegionRepository.save(serviceRegion);

        program1 = Program.builder()
                          .name("프로그램1")
                          .intro("간단한 소개")
                          .description("설명")
                          .theme("테마1")
                          .serviceRegion(serviceRegion)
                          .build();
        program2 = Program.builder()
                          .name("프로그램2")
                          .intro("간단한 소개2")
                          .description("설명2")
                          .theme("테마2")
                          .serviceRegion(serviceRegion)
                          .build();
        programRepository.save(program1);
        programRepository.save(program2);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void fail__saveProgram__if__required__parameter__is__empty() {
        Program invalidProgram = Program.builder()
                                        .intro("간단한 소개2")
                                        .description("설명2")
                                        .theme("테마2")
                                        .serviceRegion(serviceRegion)
                                        .build();

        programRepository.save(invalidProgram);
    }

    @Test
    public void findById() {
        Optional<Program> programOptional = programRepository.findById(program1.getId());
        programOptional.ifPresent(program -> {
            assertThat(program.getName()).isEqualTo(program1.getName());
            assertThat(program.getDescription()).isEqualTo(program1.getDescription());
        });
    }

    @Test
    public void findAllByRegionCode() {
        List<Program> allByServiceRegion = programRepository.findAllByServiceRegionCode(serviceRegion.getCode());

        assertThat(allByServiceRegion.size()).isEqualTo(2);
    }

    @Test
    public void updateProgram() {
        Program program = Program.builder()
                                 .name("프로그램1")
                                 .intro("간단한 소개")
                                 .description("설명")
                                 .theme("테마1")
                                 .serviceRegion(serviceRegion)
                                 .build();

        Program savedProgram = programRepository.save(program);

        Program modifiedProgram = Program.builder()
                                         .id(savedProgram.getId())
                                         .name("수정된 프로그램")
                                         .theme("테마222")
                                         .intro("수정된 소개")
                                         .description("수정된 내용")
                                         .serviceRegion(serviceRegion)
                                         .build();

        programRepository.save(modifiedProgram);
        programRepository.findById(savedProgram.getId()).ifPresent(p -> {
            assertThat(p.getId()).isEqualTo(modifiedProgram.getId());
            assertThat(p.getDescription()).isEqualTo(modifiedProgram.getDescription());

            assertThat(p.getName()).isEqualTo(modifiedProgram.getName());
            assertThat(p.getTheme()).isEqualTo(modifiedProgram.getTheme());
        });
    }

    @After
    public void tearDown() throws Exception {
        programRepository.deleteAll();
    }
}