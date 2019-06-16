package com.example.tour.infrastructure.hibernate.regioncode;

import com.example.tour.infrastructure.hibernate.program.Program;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramServiceRegionRepository extends JpaRepository<ProgramServiceRegion, Integer> {
    List<ProgramServiceRegion> findAllByProgram(Program program);

    List<ProgramServiceRegion> findAllByServiceRegion(ServiceRegion serviceRegion);

    void deleteAllByProgramId(Integer no);
}
