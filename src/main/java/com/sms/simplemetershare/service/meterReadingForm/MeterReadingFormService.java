package com.sms.simplemetershare.service.meterReadingForm;

import com.sms.simplemetershare.entity.Meter;
import com.sms.simplemetershare.entity.MeterReading;

import java.util.List;

public interface MeterReadingFormService {
    List<Meter> getMeterListForBuilding(Integer buildingId);

    void uploadMeterReadingList(List<MeterReading> readings);
}
