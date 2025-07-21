package com.sms.simplemetershare.repository;

import com.sms.simplemetershare.entity.Building;
import com.sms.simplemetershare.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findAllByBuildingAndBillingMonth(Building building, YearMonth billingMonth);
}
