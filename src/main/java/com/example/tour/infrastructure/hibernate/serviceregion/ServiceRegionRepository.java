package com.example.tour.infrastructure.hibernate.serviceregion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRegionRepository extends JpaRepository<ServiceRegion, Integer> {
    ServiceRegion findByCode(Integer code);

    ServiceRegion findFirstByGugunName(String gugunName);
}
