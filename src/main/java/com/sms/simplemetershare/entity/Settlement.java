package com.sms.simplemetershare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal meterAmount;
    private BigDecimal sharedAmount;
    private BigDecimal totalAmount;
    @ManyToOne
    private Invoice invoice;
    @ManyToOne
    private Apartment apartment;
}
