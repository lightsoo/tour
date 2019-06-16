package com.example.tour.domain.tour;

import com.example.tour.api.v1.request.ProgramDTO;
import com.example.tour.api.v1.response.TourInformationResponseDTO;
import com.example.tour.domain.region.ServiceRegionParser;
import com.example.tour.exception.NotFoundProgramException;
import com.example.tour.exception.NotFoundServiceRegionException;
import com.example.tour.infrastructure.hibernate.program.Program;
import com.example.tour.infrastructure.hibernate.program.ProgramRepository;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
import com.example.tour.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourInformationService {
    private final ServiceRegionParser serviceRegionParser;
    private final ProgramRepository programRepository;

    public void uploadTourInformation(File file) throws IOException {
        CsvUtil.loadObjectList(file)
               .forEach(this::saveProgram);
    }

    public void createTourInformation(TourInformation tourInformation) {
        saveProgram(tourInformation);
    }

    public void updateTourInformation(TourInformation modifiedTourInformation) {
        Program originProgram = programRepository.findById(modifiedTourInformation.getNo())
                                                 .orElseThrow(() -> new NotFoundProgramException(modifiedTourInformation.getNo()));

        Set<ServiceRegion> serviceRegionSet = new HashSet<>(serviceRegionParser.parse(modifiedTourInformation.getServiceRegionName()));
        ServiceRegion originServiceRegion = originProgram.getServiceRegion();

        if (serviceRegionSet.contains(originServiceRegion)) {
            programRepository.save(
                Program.builder()
                       .id(originProgram.getId())
                       .name(modifiedTourInformation.getName())
                       .theme(modifiedTourInformation.getTheme())
                       .intro(modifiedTourInformation.getIntro())
                       .description(modifiedTourInformation.getDescription())
                       .serviceRegion(originServiceRegion)
                       .serviceRegionName(modifiedTourInformation.getServiceRegionName())
                       .build()
            );

            serviceRegionSet.remove(originServiceRegion);
        }

        saveProgramIfServiceRegionAdded(modifiedTourInformation, serviceRegionSet);
    }

    public TourInformationResponseDTO getTourInformationListByServiceRegionCode(String serviceRegionName) {
        ServiceRegion serviceRegion = serviceRegionParser.parse(serviceRegionName).stream().findFirst()
                                                         .orElseThrow(() -> new NotFoundServiceRegionException(serviceRegionName));

        List<ProgramDTO> programDTOList = programRepository.findAllByServiceRegion(serviceRegion)
                                                           .stream()
                                                           .map(program -> {
                                                               ProgramDTO programDTO = new ProgramDTO();
                                                               programDTO.setName(program.getName());
                                                               programDTO.setTheme(program.getTheme());
                                                               return programDTO;
                                                           })
                                                           .collect(Collectors.toList());

        return new TourInformationResponseDTO((long) serviceRegion.getCode(), programDTOList);
    }

    public ProgramDTO getTourInformationByProgramId(Integer programId) {
        Program program = programRepository.findById(programId)
                                           .orElseThrow(() -> new NotFoundProgramException(programId));

        ProgramDTO programDTO = new ProgramDTO();
        programDTO.setId(program.getId());
        programDTO.setName(program.getName());
        programDTO.setTheme(program.getTheme());
        programDTO.setIntro(program.getIntro());
        programDTO.setDescription(program.getDescription());
        programDTO.setServiceRegionName(program.getServiceRegionName());

        return programDTO;
    }

    private void saveProgram(TourInformation tourInformation) {
        serviceRegionParser.parse(tourInformation.getServiceRegionName())
                           .forEach(serviceRegion -> programRepository.save(
                               Program.builder()
                                      .name(tourInformation.getName())
                                      .theme(tourInformation.getTheme())
                                      .intro(tourInformation.getIntro())
                                      .description(tourInformation.getDescription())
                                      .serviceRegionName(tourInformation.getServiceRegionName())
                                      .serviceRegion(serviceRegion)
                                      .build()
                           ));
    }

    private void saveProgramIfServiceRegionAdded(TourInformation modifiedTourInformation, Set<ServiceRegion> serviceRegionList) {
        serviceRegionList.forEach(serviceRegion -> programRepository.save(
            Program.builder()
                   .name(modifiedTourInformation.getName())
                   .theme(modifiedTourInformation.getTheme())
                   .intro(modifiedTourInformation.getIntro())
                   .description(modifiedTourInformation.getDescription())
                   .serviceRegionName(modifiedTourInformation.getServiceRegionName())
                   .serviceRegion(serviceRegion)
                   .build()
        ));
    }
}

