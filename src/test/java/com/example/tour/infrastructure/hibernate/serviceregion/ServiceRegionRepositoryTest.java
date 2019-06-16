package com.example.tour.infrastructure.hibernate.serviceregion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceRegionRepositoryTest {
    @Autowired
    private ServiceRegionRepository repository;

    @Test
    public void findFirstByGugunName() {
        assertThat(repository.findFirstByGugunName("평창군")).isNotNull();
        assertThat(repository.findFirstByGugunName("동구")).isNotNull();
    }

}