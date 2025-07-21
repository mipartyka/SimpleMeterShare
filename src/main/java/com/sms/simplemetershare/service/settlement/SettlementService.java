package com.sms.simplemetershare.service.settlement;

import com.sms.simplemetershare.entity.Settlement;

import java.time.YearMonth;

public interface SettlementService {
    Settlement getSettlementForYearMonth(YearMonth yearMonth, Integer apartmentId);
}
