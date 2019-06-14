package com.example.tour.infrastructure.hibernate.program;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
//    Program findByRegionCode(String regionCode);
}
