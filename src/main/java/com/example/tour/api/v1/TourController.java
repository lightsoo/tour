package com.example.tour.api.v1;

import com.example.tour.domain.tour.TourInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(path = "/tour/v1")
@RequiredArgsConstructor
public class TourController {
    public static final String uploadingDir = System.getProperty("user.dir") + "/uploadingDir/";

    private final TourInformationService tourInformationService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadTourInformationFile(@RequestParam("file") MultipartFile multipartFile) {
        File file = new File(uploadingDir + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
            tourInformationService.uploadTourInformation(file);
        } catch (IOException e) {
            throw new RuntimeException("Failt to upload file");
        }
    }

}
