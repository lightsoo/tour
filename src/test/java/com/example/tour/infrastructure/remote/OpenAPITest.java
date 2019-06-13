package com.example.tour.infrastructure.remote;

import com.example.tour.infrastructure.remote.openAPI.OpenAPI;
import com.example.tour.infrastructure.remote.openAPI.OpenAPIResponseDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OpenAPITest {
    @Autowired
    private OpenAPI openAPI;

    @Test
    public void success__getRegionCode() {
        OpenAPIResponseDTO response = openAPI.getRegionCode("광주", 100);

        assertThat(response).isNotNull();
        assertThat(response.getResponse().getHeader().getResultCode()).isNotNull();
    }
}