package com.sms.simplemetershare.service.invoiceReadingForm;

import com.sms.simplemetershare.entity.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceReadingFormService {
    void uploadInvoiceList(List<Invoice> invoiceList);
}
