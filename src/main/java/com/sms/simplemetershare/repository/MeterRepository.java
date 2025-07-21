package com.sms.simplemetershare.repository;

import com.sms.simplemetershare.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterRepository extends JpaRepository<Meter, Integer> {
    List<Meter> findAllByApartments_Building_Id(Integer buildingId);
}
