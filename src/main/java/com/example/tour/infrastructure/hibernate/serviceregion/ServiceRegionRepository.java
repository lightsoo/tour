package com.example.tour.infrastructure.hibernate.serviceregion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRegionRepository extends JpaRepository<ServiceRegion, Integer> {
    List<ServiceRegion> findAllByNameContains(String name);

    ServiceRegion findByCode(Integer code);

    ServiceRegion findByName(String name);
}
