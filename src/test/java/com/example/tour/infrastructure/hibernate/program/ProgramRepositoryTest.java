package com.example.tour.infrastructure.hibernate.program;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProgramRepositoryTest {

    @Autowired
    private ProgramRepository repository;
    private Program program;

    @Before
    public void setUp() throws Exception {
//        program = new Program("123", "자연과 문화를 함께 즐기는 설악산 기행", "문화생태체험,자연생태체험", "강원도 속초", "서", "설명");
        repository.save(program);
    }

    @Test
    public void name() {
        Program result = repository.findByRegionCode("123");

        assertEquals("123", result.getRegionCode());
    }

    @After
    public void tearDown() throws Exception {
    }
}