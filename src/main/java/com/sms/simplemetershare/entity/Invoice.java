package com.sms.simplemetershare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private BigDecimal amount;
    private YearMonth billingMonth;
    private LocalDate issuedDate;
    @ManyToOne
    private Building building;
}
