package com.example.tour.domain.tour;

import com.example.tour.domain.region.ServiceRegionParser;
import com.example.tour.infrastructure.hibernate.program.Program;
import com.example.tour.infrastructure.hibernate.program.ProgramRepository;
import com.example.tour.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TourInformationService {
    private final ServiceRegionParser serviceRegionParser;
    private final ProgramRepository programRepository;

    public void uploadTourInformation(File file) throws IOException {
        CsvUtil.loadObjectList(file)
               .forEach(this::saveProgram);
    }

    private void saveProgram(TourInformation tourInformation) {
        serviceRegionParser.parse(tourInformation.getServiceRegion())
                           .forEach(serviceRegion -> programRepository.save(
                               Program.builder()
                                      .name(tourInformation.getName())
                                      .theme(tourInformation.getTheme())
                                      .intro(tourInformation.getIntro())
                                      .description(tourInformation.getDescription())
                                      .serviceRegion(serviceRegion)
                                      .build()
                                    )
                           );
    }

}

