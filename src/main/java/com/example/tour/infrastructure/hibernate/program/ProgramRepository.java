package com.example.tour.infrastructure.hibernate.program;

import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
    List<Program> findAllByServiceRegion(ServiceRegion serviceRegion);

    List<Program> findAllByServiceRegionCode(Integer code);
}
