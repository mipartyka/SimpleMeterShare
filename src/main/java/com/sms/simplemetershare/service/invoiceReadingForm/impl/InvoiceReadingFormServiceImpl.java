package com.sms.simplemetershare.service.invoiceReadingForm.impl;

import com.sms.simplemetershare.entity.Invoice;
import com.sms.simplemetershare.repository.InvoiceRepository;
import com.sms.simplemetershare.service.invoiceReadingForm.InvoiceReadingFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceReadingFormServiceImpl implements InvoiceReadingFormService {
    private final InvoiceRepository invoiceRepository;

    @Override
    public void uploadInvoiceList(List<Invoice> invoiceList) {
        invoiceRepository.saveAll(invoiceList);
    }
}
