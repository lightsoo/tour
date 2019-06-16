package com.example.tour.domain.tour;

import com.example.tour.api.v1.request.ProgramDTO;
import com.example.tour.api.v1.response.TourInformationResponseDTO;
import com.example.tour.domain.region.ServiceRegionParser;
import com.example.tour.exception.NotFoundProgramException;
import com.example.tour.exception.NotFoundServiceRegionException;
import com.example.tour.infrastructure.hibernate.program.Program;
import com.example.tour.infrastructure.hibernate.program.ProgramRepository;
import com.example.tour.infrastructure.hibernate.regioncode.ProgramServiceRegion;
import com.example.tour.infrastructure.hibernate.regioncode.ProgramServiceRegionRepository;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegionRepository;
import com.example.tour.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourInformationService {
    private final ServiceRegionParser serviceRegionParser;
    private final ProgramRepository programRepository;
    private final ProgramServiceRegionRepository programServiceRegionRepository;
    private final ServiceRegionRepository serviceRegionRepository;

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

        Program modifiedProgram = Program.builder()
                                         .id(originProgram.getId())
                                         .name(modifiedTourInformation.getName())
                                         .theme(modifiedTourInformation.getTheme())
                                         .intro(modifiedTourInformation.getIntro())
                                         .description(modifiedTourInformation.getDescription())
                                         .serviceRegionName(modifiedTourInformation.getServiceRegionName())
                                         .build();

        if (isServiceRegionNameModified(modifiedTourInformation, originProgram)) {
            //TODO: Fix: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
            programServiceRegionRepository.deleteAllByProgramId(modifiedTourInformation.getNo());

            programRepository.save(modifiedProgram);

            serviceRegionParser.parse(modifiedTourInformation.getServiceRegionName())
                               .forEach(serviceRegion ->
                                            programServiceRegionRepository.save(
                                                ProgramServiceRegion.builder()
                                                                    .serviceRegion(serviceRegion)
                                                                    .program(modifiedProgram)
                                                                    .build()
                                            ));
        } else {
            programRepository.save(modifiedProgram);
        }
    }

    public TourInformationResponseDTO getTourInformationListByServiceRegionCode(String serviceRegionName) {
        ServiceRegion serviceRegion = serviceRegionRepository.findFirstByGugunName(serviceRegionName);
        List<Integer> programIdList = programServiceRegionRepository.findAllByServiceRegion(serviceRegion)
                                                                    .stream()
                                                                    .map(programServiceRegion -> programServiceRegion.getProgram().getId())
                                                                    .collect(Collectors.toList());

        List<ProgramDTO> programDTOList = programRepository.findAllById(programIdList)
                                                           .stream()
                                                           .map(program -> {
                                                               ProgramDTO programDTO = new ProgramDTO();
                                                               programDTO.setName(program.getName());
                                                               programDTO.setTheme(program.getTheme());
                                                               return programDTO;
                                                           })
                                                           .collect(Collectors.toList());

        return new TourInformationResponseDTO(serviceRegion.getCode(), programDTOList);
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
        Program program = Program.builder()
                                 .name(tourInformation.getName())
                                 .theme(tourInformation.getTheme())
                                 .intro(tourInformation.getIntro())
                                 .description(tourInformation.getDescription())
                                 .serviceRegionName(tourInformation.getServiceRegionName())
                                 .build();

        programRepository.save(program);

        serviceRegionParser.parse(tourInformation.getServiceRegionName())
                           .forEach(serviceRegion ->
                                        programServiceRegionRepository.save(
                                            ProgramServiceRegion.builder()
                                                                .serviceRegion(serviceRegion)
                                                                .program(program)
                                                                .build()
                                        )
                           );
    }

    private boolean isServiceRegionNameModified(TourInformation modifiedTourInformation, Program originProgram) {
        return !modifiedTourInformation.getServiceRegionName().equals(originProgram.getServiceRegionName());
    }
}

