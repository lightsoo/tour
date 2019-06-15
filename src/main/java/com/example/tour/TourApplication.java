package com.example.tour;

import com.example.tour.api.v1.TourController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.File;

@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class TourApplication {

    public static void main(String[] args) {
        new File(TourController.uploadingDir).mkdirs();
        SpringApplication.run(TourApplication.class, args);
    }

}
