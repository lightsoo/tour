package com.example.tour.infrastructure.hibernate.program;

import com.example.tour.infrastructure.hibernate.regioncode.ProgramServiceRegion;
import com.example.tour.infrastructure.hibernate.regioncode.ProgramServiceRegionRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProgramRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private ServiceRegionRepository serviceRegionRepository;
    @Autowired
    private ProgramServiceRegionRepository programServiceRegionRepository;

    private ServiceRegion serviceRegion;
    private String serviceRegionName;

    @Before
    public void setUp() throws Exception {
        serviceRegionName = "광주광역시";
        serviceRegion = serviceRegionRepository.findByName(serviceRegionName);
    }

    @Test
    public void saveProgram() {
        Program program = Program.builder()
                                 .name("프로그램2")
                                 .intro("간단한 소개2")
                                 .description("설명2")
                                 .serviceRegionName(serviceRegionName)
                                 .theme("테마2")
                                 .build();

        programRepository.save(program);

        programServiceRegionRepository.save(
            ProgramServiceRegion.builder()
                                .serviceRegion(serviceRegion)
                                .program(program)
                                .build()
        );
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void fail__saveProgram__if__required__parameter__is__empty() {
        Program invalidProgram = Program.builder()
                                        .intro("간단한 소개2")
                                        .description("설명2")
                                        .theme("테마2")
                                        .build();

        programRepository.save(invalidProgram);
    }

    @Test
    public void findById() {
        Program program = Program.builder()
                                 .name("프로그램1")
                                 .intro("간단한 소개")
                                 .description("설명")
                                 .serviceRegionName(serviceRegionName)
                                 .theme("테마1")
                                 .build();

        programRepository.save(program);

        programServiceRegionRepository.save(
            ProgramServiceRegion.builder()
                                .serviceRegion(serviceRegion)
                                .program(program)
                                .build()
        );

        programRepository.findById(program.getId()).ifPresent(p -> {
            assertThat(p.getName()).isEqualTo(program.getName());
            assertThat(p.getDescription()).isEqualTo(program.getDescription());
        });
    }

    @Test
    public void updateProgram() {
        Program program = Program.builder()
                                 .name("프로그램1")
                                 .intro("간단한 소개")
                                 .description("설명")
                                 .theme("테마1")
                                 .serviceRegionName("광주광역시")
                                 .build();

        Program savedProgram = programRepository.save(program);

        Program modifiedProgram = Program.builder()
                                         .id(savedProgram.getId())
                                         .name("수정된 프로그램")
                                         .theme("테마222")
                                         .intro("수정된 소개")
                                         .description("수정된 내용")
                                         .serviceRegionName("광주광역시")
                                         .build();

        programRepository.save(modifiedProgram);
        programRepository.findById(savedProgram.getId()).ifPresent(p -> {
            assertThat(p.getId()).isEqualTo(modifiedProgram.getId());
            assertThat(p.getDescription()).isEqualTo(modifiedProgram.getDescription());

            assertThat(p.getName()).isEqualTo(modifiedProgram.getName());
            assertThat(p.getTheme()).isEqualTo(modifiedProgram.getTheme());
        });

        assertThat(programRepository.findAll().size()).isEqualTo(1);
    }

    @After
    public void tearDown() {
        programRepository.deleteAll();
    }
}