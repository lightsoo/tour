package com.example.tour.api.v1;

import com.example.tour.api.v1.request.ProgramDTO;
import com.example.tour.api.v1.request.TourInformationRequestDTO;
import com.example.tour.api.v1.response.TourInformationResponseDTO;
import com.example.tour.domain.tour.TourInformation;
import com.example.tour.domain.tour.TourInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/tour/v1", description = "생태관광 정보 관리", tags = {"생태관광 정보 관리 APIs"})
public class TourController {
    public static final String UPLOADING_DIRECTORY = System.getProperty("user.dir") + "/upload/";

    private final TourInformationService tourInformationService;

    @ApiOperation(value = "CSV파일 업로드", notes = "CSV파일을 업로드")
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

    @ApiOperation(value = "단일 Program 조회", response = ProgramDTO.class)
    @GetMapping(value = "/program/{programId}")
    public ProgramDTO getTourInformation(@PathVariable Integer programId) {
        return tourInformationService.getTourInformationByProgramId(programId);
    }

    @ApiOperation(value = "단일 Program 생성")
    @PostMapping(value = "/program")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourInformation( @RequestBody @Valid TourInformationRequestDTO request) {
        TourInformation tourInformation = TourInformationRequestDTO.convert(request);

        tourInformationService.createTourInformation(tourInformation);
    }

    @ApiOperation(value = "Program 수정")
    @PutMapping(value = "/program")
    public void modifyTourInformation(@RequestBody TourInformationRequestDTO request) {
        TourInformation modifiedTourInformation = TourInformationRequestDTO.convert(request);

        tourInformationService.updateTourInformation(modifiedTourInformation);
    }

    @ApiOperation(value = "서비스지역코드 기반 Program 조회", response = TourInformationResponseDTO.class)
    @GetMapping(value = "/serviceRegion/{serviceRegionName}")
    public TourInformationResponseDTO getTourInformationByServiceRegionCode(@PathVariable String serviceRegionName) {
        return tourInformationService.getTourInformationListByServiceRegionCode(serviceRegionName);
    }

}
