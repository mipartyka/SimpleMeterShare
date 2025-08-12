package com.sms.simplemetershare.service.invoiceReadingForm;

import com.sms.simplemetershare.entity.Invoice;

import java.util.List;

public interface InvoiceReadingFormService {
    void uploadInvoiceList(List<Invoice> invoiceList);
}
