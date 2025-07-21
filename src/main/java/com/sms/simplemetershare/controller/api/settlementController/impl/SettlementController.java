package com.sms.simplemetershare.controller.api.settlementController.impl;

import com.sms.simplemetershare.controller.api.settlementController.SettlementControllerApi;
import com.sms.simplemetershare.entity.Settlement;
import com.sms.simplemetershare.service.settlement.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequiredArgsConstructor
public class SettlementController implements SettlementControllerApi {
    private final SettlementService settlementService;

    @Override
    public ResponseEntity<Settlement> getSettlementForYearMonth(YearMonth yearMonth, Integer apartmentId) {
        return ResponseEntity.ok(settlementService.getSettlementForYearMonth(yearMonth, apartmentId));
    }
}
