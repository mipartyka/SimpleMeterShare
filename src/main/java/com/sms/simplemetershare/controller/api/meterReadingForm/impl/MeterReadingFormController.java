package com.sms.simplemetershare.controller.api.meterReadingForm.impl;

import com.sms.simplemetershare.controller.api.meterReadingForm.MeterReadingFormControllerApi;
import com.sms.simplemetershare.entity.Meter;
import com.sms.simplemetershare.entity.MeterReading;
import com.sms.simplemetershare.service.meterReadingForm.MeterReadingFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MeterReadingFormController implements MeterReadingFormControllerApi {
    private final MeterReadingFormService meterReadingFormService;

    @Override
    public ResponseEntity<List<Meter>> initMeterReadingForm(Integer BuildingId) {
        return ResponseEntity.ok(meterReadingFormService.getMeterListForBuilding(BuildingId));
    }

    @Override
    public ResponseEntity<String> uploadMeterReadingForm(List<MeterReading> readings) {
        meterReadingFormService.uploadMeterReadingList(readings);
        return ResponseEntity.ok("Meter Readings Successfully Uploaded");
    }
}
