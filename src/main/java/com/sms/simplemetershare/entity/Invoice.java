package com.sms.simplemetershare.entity;

import com.sms.simplemetershare.entity.enummerated.InvoiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private InvoiceType type;
    private BigDecimal amount;
    private String billingMonth;
    private LocalDate issuedDate;
    @ManyToOne
    private Building building;
}
