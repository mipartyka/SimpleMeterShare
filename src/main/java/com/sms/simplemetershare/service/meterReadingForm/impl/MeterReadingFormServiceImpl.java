package com.sms.simplemetershare.service.meterReadingForm.impl;

import com.sms.simplemetershare.entity.Meter;
import com.sms.simplemetershare.entity.MeterReading;
import com.sms.simplemetershare.repository.MeterReadingRepository;
import com.sms.simplemetershare.repository.MeterRepository;
import com.sms.simplemetershare.service.meterReadingForm.MeterReadingFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeterReadingFormServiceImpl implements MeterReadingFormService {
    private final MeterRepository meterRepository;
    private final MeterReadingRepository meterReadingRepository;

    @Override
    public List<Meter> getMeterListForBuilding(Integer buildingId) {
        return meterRepository.findAllByApartments_Building_Id(buildingId);
    }

    @Override
    public void uploadMeterReadingList(List<MeterReading> readings) {
        meterReadingRepository.saveAll(readings);
    }
}
