package com.sms.simplemetershare.entity;

import com.sms.simplemetershare.entity.enummerate.MeterType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Meter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private MeterType type;
    @ManyToMany
    private List<Apartment> apartments;
    @ManyToOne
    private Building building;
    private String serialNumber;
}
