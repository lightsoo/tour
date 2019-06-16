package com.example.tour.api.v1;

import com.example.tour.api.v1.request.ProgramDTO;
import com.example.tour.api.v1.request.TourInformationRequestDTO;
import com.example.tour.api.v1.response.TourInformationResponseDTO;
import com.example.tour.domain.tour.TourInformation;
import com.example.tour.domain.tour.TourInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(path = "/tour/v1")
@RequiredArgsConstructor
public class TourController {
    public static final String UPLOADING_DIRECTORY = System.getProperty("user.dir") + "/upload/";

    private final TourInformationService tourInformationService;

    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
    public void uploadTourInformationFile(@RequestParam("file") MultipartFile multipartFile) {
        File file = new File(UPLOADING_DIRECTORY + multipartFile.getOriginalFilename());

        try {
            multipartFile.transferTo(file);
            tourInformationService.uploadTourInformation(file);
        } catch (IOException e) {
            throw new RuntimeException("Fail to upload file");
        }
    }

    @GetMapping(value = "/program/{programId}")
    public ProgramDTO getTourInformation(@PathVariable Integer programId) {
        return tourInformationService.getTourInformationByProgramId(programId);
    }

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourInformation(@RequestBody @Valid TourInformationRequestDTO request) {
        TourInformation tourInformation = TourInformationRequestDTO.convert(request);

        tourInformationService.createTourInformation(tourInformation);
    }

    @PutMapping(value = "/")
    public void modifyTourInformation(@RequestBody TourInformationRequestDTO request) {
        TourInformation modifiedTourInformation = TourInformationRequestDTO.convert(request);

        tourInformationService.updateTourInformation(modifiedTourInformation);
    }

    @GetMapping(value = "/serviceRegion/{serviceRegionName}")
    public TourInformationResponseDTO getTourInformationByServiceRegionCode(@PathVariable String serviceRegionName) {
        return tourInformationService.getTourInformationListByServiceRegionCode(serviceRegionName);
    }

}
