package com.example.tour;

import com.example.tour.api.v1.TourController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;

@EnableSwagger2
@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class TourApplication {

    public static void main(String[] args) {
        new File(TourController.UPLOADING_DIRECTORY).mkdirs();
        SpringApplication.run(TourApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage("com.example.tour"))
                                                      .paths(PathSelectors.any())
                                                      .build();
    }
}
