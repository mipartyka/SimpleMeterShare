package com.sms.simplemetershare.repository;

import com.sms.simplemetershare.entity.Apartment;
import com.sms.simplemetershare.entity.Building;
import com.sms.simplemetershare.entity.MeterReading;
import com.sms.simplemetershare.entity.enummerate.MeterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Integer> {
    List<MeterReading> findAllByMeter_ApartmentsAndReadingMonth(List<Apartment> meter_apartment, YearMonth readingMonth);

    List<MeterReading> findAllByMeter_BuildingAndReadingMonth(Building building, YearMonth yearMonth);

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
            @Param("month") YearMonth month,
            @Param("type") MeterType type
    );
}
