package com.sms.simplemetershare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate readingDate;
    private Double readingValue;
    private Month readingMonth;
    private Year readingYear;
    @ManyToOne
    private Meter meter;
}
