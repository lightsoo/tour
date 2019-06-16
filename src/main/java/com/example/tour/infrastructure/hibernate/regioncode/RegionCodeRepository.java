package com.example.tour.infrastructure.hibernate.regioncode;

import com.example.tour.infrastructure.hibernate.program.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionCodeRepository extends JpaRepository<RegionCode, Integer> {
    List<RegionCode> findAllByProgram(Program program);

    List<RegionCode> findAllByCode(Integer code);
}
