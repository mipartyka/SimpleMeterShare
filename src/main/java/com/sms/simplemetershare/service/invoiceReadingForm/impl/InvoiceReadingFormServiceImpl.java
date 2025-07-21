package com.sms.simplemetershare.service.invoiceReadingForm.impl;

import com.sms.simplemetershare.entity.Invoice;
import com.sms.simplemetershare.repository.InvoiceRepository;
import com.sms.simplemetershare.service.invoiceReadingForm.InvoiceReadingFormService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InvoiceReadingFormServiceImpl implements InvoiceReadingFormService {
    private final InvoiceRepository invoiceRepository;

    @Override
    public void uploadInvoiceList(List<Invoice> invoiceList) {
        invoiceRepository.saveAll(invoiceList);
    }
}
