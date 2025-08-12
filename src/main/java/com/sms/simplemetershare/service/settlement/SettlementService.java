package com.sms.simplemetershare.service.settlement;

import com.sms.simplemetershare.entity.Settlement;

public interface SettlementService {
    Settlement getSettlementForYearMonth(String yearMonth, Integer apartmentId);
}
