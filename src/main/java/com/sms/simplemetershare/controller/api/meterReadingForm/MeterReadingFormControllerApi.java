package com.sms.simplemetershare.controller.api.meterReadingForm;

import com.sms.simplemetershare.entity.Meter;
import com.sms.simplemetershare.entity.MeterReading;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/meter-form")
public interface MeterReadingFormControllerApi {
    @GetMapping
    ResponseEntity<List<Meter>> initMeterReadingForm(@RequestParam Integer BuildingId);
    @PostMapping
    ResponseEntity<String> uploadMeterReadingForm(@RequestBody List<MeterReading> readings);
}
