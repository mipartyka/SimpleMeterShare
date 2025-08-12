package com.sms.simplemetershare.repository;

import com.sms.simplemetershare.entity.Apartment;
import com.sms.simplemetershare.entity.Building;
import com.sms.simplemetershare.entity.MeterReading;
import com.sms.simplemetershare.entity.enummerated.MeterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Integer> {

    List<MeterReading> findAllByMeter_ApartmentsInAndReadingMonth(
            List<Apartment> meter_apartment, String readingMonth
    );

    List<MeterReading> findAllByMeter_BuildingAndReadingMonth(
            Building building, String readingMonth
    );

    @Query("""
        SELECT SUM(mr.readingValue)
        FROM MeterReading mr
        JOIN mr.meter m
        JOIN m.apartments a
        WHERE mr.readingMonth = :month
          AND a IN :apartments
          AND m.type = :type
    """)
    BigDecimal sumSharedMeterReadingsByApartmentsAndMonthAndType(
            @Param("apartments") List<Apartment> apartments,
            @Param("month") String month,
            @Param("type") MeterType type
    );
}

