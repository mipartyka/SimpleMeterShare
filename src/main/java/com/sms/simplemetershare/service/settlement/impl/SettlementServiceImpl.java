package com.sms.simplemetershare.service.settlement.impl;

import com.sms.simplemetershare.entity.*;
import com.sms.simplemetershare.entity.enummerated.InvoiceType;
import com.sms.simplemetershare.entity.enummerated.MeterType;
import com.sms.simplemetershare.repository.ApartmentRepository;
import com.sms.simplemetershare.repository.InvoiceRepository;
import com.sms.simplemetershare.repository.MeterReadingRepository;
import com.sms.simplemetershare.service.settlement.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {
    private final ApartmentRepository apartmentRepository;
    private final MeterReadingRepository meterReadingRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public Settlement getSettlementForYearMonth(String yearMonth, Integer apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId).orElse(null);
        if (Objects.isNull(apartment)) {
            throw new RuntimeException("Apartment with id " + apartmentId + " not found");
        }
        Building building = apartment.getBuilding();
        List<MeterReading> apartmentReadings =
                meterReadingRepository.findAllByMeter_ApartmentsInAndReadingMonth(List.of(apartment), yearMonth);
        List<MeterReading> buildingReadings =
                meterReadingRepository.findAllByMeter_BuildingAndReadingMonth(building, yearMonth);
        List<Invoice> buildingInvoices =
                invoiceRepository.findAllByBuildingAndBillingMonth(building, yearMonth);

        BigDecimal coldWaterCost = calculateColdWaterCost(apartmentReadings, buildingReadings, buildingInvoices);
        BigDecimal electricityCost = calculateElectricityCost(apartmentReadings, buildingReadings, buildingInvoices);
        BigDecimal gasCost = calculateGasCost(apartmentReadings, buildingReadings, buildingInvoices);
        BigDecimal hotWaterCost = calculateHotWaterCost(apartmentReadings, buildingReadings, buildingInvoices, apartment, yearMonth);

        return null;
    }

    private BigDecimal calculateHotWaterCost(
            List<MeterReading> apartmentReadings,
            List<MeterReading> buildingReadings,
            List<Invoice> buildingInvoices,
            Apartment apartment,
            String yearMonth
    ) {
        MeterReading apartmentHotWaterReading = getHotWaterReadingForApartment(apartmentReadings);
        BigDecimal usage = BigDecimal.valueOf(apartmentHotWaterReading.getReadingValue());

        BigDecimal unitCost = calculateHotWaterUnitCost(
                buildingReadings, buildingInvoices, apartment, yearMonth, apartmentHotWaterReading);

        return unitCost.multiply(usage);
    }

    private BigDecimal calculateHotWaterUnitCost(
            List<MeterReading> buildingReadings,
            List<Invoice> buildingInvoices,
            Apartment apartment,
            String yearMonth,
            MeterReading apartmentHotWaterReading
    ) {
        MeterReading sharedMeterReading = findSharedHotWaterReading(buildingReadings, apartment);
        List<Apartment> sharedApartments = sharedMeterReading.getMeter().getApartments();

        BigDecimal totalSharedUsage = meterReadingRepository.sumSharedMeterReadingsByApartmentsAndMonthAndType(
                sharedApartments, yearMonth, MeterType.HOT_WATER
        );

        if (totalSharedUsage == null || totalSharedUsage.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Cannot divide by zero – total shared usage is zero");
        }

        BigDecimal sharedMeterValue = BigDecimal.valueOf(sharedMeterReading.getReadingValue());
        BigDecimal apartmentUsage = BigDecimal.valueOf(apartmentHotWaterReading.getReadingValue());

        BigDecimal hotWaterCost = sharedMeterValue
                .multiply(apartmentUsage)
                .divide(totalSharedUsage, 4, RoundingMode.HALF_UP);

        BigDecimal coldWaterUnitCost = calculateColdWaterUnitCost(buildingReadings, buildingInvoices);
        BigDecimal coldWaterCost = coldWaterUnitCost.multiply(apartmentUsage);

        return hotWaterCost.add(coldWaterCost);
    }

    private MeterReading getHotWaterReadingForApartment(List<MeterReading> apartmentReadings) {
        return apartmentReadings.stream()
                .filter(r -> r.getMeter().getType().equals(MeterType.HOT_WATER))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Missing hot water meter reading for apartment"));
    }

    private MeterReading findSharedHotWaterReading(List<MeterReading> buildingReadings, Apartment apartment) {
        return buildingReadings.stream()
                .filter(r -> r.getMeter().getType().equals(MeterType.HOT_WATER))
                .filter(r -> r.getMeter().getApartments().contains(apartment))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No shared hot water meter found for apartment"));
    }


    private BigDecimal calculateGasCost(List<MeterReading> apartmentReadings, List<MeterReading> buildingReadings, List<Invoice> buildingInvoices) {
        MeterReading apartmentGas = apartmentReadings.stream()
                .filter(r -> r.getMeter().getType().equals(MeterType.GAS))
                .findFirst().orElse(null);

        if (apartmentGas == null)
            throw new RuntimeException("Missing gas meter readings");

        return calculateGasUnitCost(buildingReadings, buildingInvoices).multiply(BigDecimal.valueOf(apartmentGas.getReadingValue()));
    }

    private BigDecimal calculateElectricityCost(List<MeterReading> apartmentReadings, List<MeterReading> buildingReadings, List<Invoice> buildingInvoices) {
        MeterReading apartmentElectricity = apartmentReadings.stream()
                .filter(r -> r.getMeter().getType().equals(MeterType.ELECTRICITY))
                .findFirst().orElse(null);

        if (apartmentElectricity == null)
            throw new RuntimeException("Missing electricity meter readings");

        return calculateColdWaterUnitCost(buildingReadings, buildingInvoices).multiply(BigDecimal.valueOf(apartmentElectricity.getReadingValue()));
    }

    private BigDecimal calculateColdWaterCost(List<MeterReading> apartmentReadings, List<MeterReading> buildingReadings, List<Invoice> buildingInvoices) {
        MeterReading apartmentCold = apartmentReadings.stream()
                .filter(r -> r.getMeter().getType().equals(MeterType.COLD_WATER))
                .findFirst().orElse(null);

        if (apartmentCold == null)
            throw new RuntimeException("Missing cold water meter readings");

        return calculateElectricityUnitCost(buildingReadings, buildingInvoices).multiply(BigDecimal.valueOf(apartmentCold.getReadingValue()));
    }


    private BigDecimal calculateColdWaterUnitCost(List<MeterReading> buildingReadings,
                                                  List<Invoice> buildingInvoices) {
        Invoice waterInvoice = buildingInvoices.stream()
                .filter(invoice -> invoice.getType().equals(InvoiceType.WATER))
                .findFirst().orElse(null);

        MeterReading buildingCold = buildingReadings.stream()
                .filter(r -> r.getMeter().getType().equals(MeterType.COLD_WATER))
                .findFirst().orElse(null);

        if (waterInvoice == null || buildingCold == null)
            throw new RuntimeException("Missing cold water invoice or meter readings");

        if (buildingCold.getReadingValue() == 0)
            throw new ArithmeticException("Building cold water reading is zero – cannot divide");

        return waterInvoice.getAmount()
                .divide(BigDecimal.valueOf(buildingCold.getReadingValue()), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateElectricityUnitCost(List<MeterReading> buildingReadings,
                                                    List<Invoice> buildingInvoices) {
        Invoice electricityInvoice = buildingInvoices.stream()
                .filter(invoice -> invoice.getType().equals(InvoiceType.ELECTRICITY))
                .findFirst().orElse(null);

        MeterReading buildingElectricity = buildingReadings.stream()
                .filter(r -> r.getMeter().getType().equals(MeterType.ELECTRICITY))
                .findFirst().orElse(null);

        if (electricityInvoice == null || buildingElectricity == null)
            throw new RuntimeException("Missing electricity invoice or meter readings");

        if (buildingElectricity.getReadingValue() == 0)
            throw new ArithmeticException("Building electricity reading is zero – cannot divide");

        return electricityInvoice.getAmount()
                .divide(BigDecimal.valueOf(buildingElectricity.getReadingValue()), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateGasUnitCost(List<MeterReading> buildingReadings,
                                                List<Invoice> buildingInvoices) {
        Invoice gasInvoice = buildingInvoices.stream()
                .filter(invoice -> invoice.getType().equals(InvoiceType.GAS))
                .findFirst().orElse(null);

        MeterReading buildingGas = buildingReadings.stream()
                .filter(r -> r.getMeter().getType().equals(MeterType.GAS))
                .findFirst().orElse(null);

        if (gasInvoice == null || buildingGas == null)
            throw new RuntimeException("Missing gas invoice or meter readings");

        if (buildingGas.getReadingValue() == 0)
            throw new ArithmeticException("Building gas reading is zero – cannot divide");

        return gasInvoice.getAmount()
                .divide(BigDecimal.valueOf(buildingGas.getReadingValue()), 2, RoundingMode.HALF_UP);
    }

}
