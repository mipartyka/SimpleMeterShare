package com.sms.simplemetershare.repository;

import com.sms.simplemetershare.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
}
