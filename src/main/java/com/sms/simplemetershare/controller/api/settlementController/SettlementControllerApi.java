package com.sms.simplemetershare.controller.api.settlementController;

import com.sms.simplemetershare.entity.Settlement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;

@RequestMapping("/v1/settlement")
public interface SettlementControllerApi {
    @GetMapping
    ResponseEntity<Settlement> getSettlementForYearMonth(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
            @RequestParam Integer apartmentId);
}
