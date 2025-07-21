package com.sms.simplemetershare.controller.api.invoiceReadingForm.impl;

import com.sms.simplemetershare.controller.api.invoiceReadingForm.InvoiceReadingFormControllerApi;
import com.sms.simplemetershare.entity.Invoice;
import com.sms.simplemetershare.service.invoiceReadingForm.InvoiceReadingFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InvoiceReadingFormController implements InvoiceReadingFormControllerApi {
    private final InvoiceReadingFormService invoiceReadingFormService;

    @Override
    public ResponseEntity<String> uploadInvoiceForm(List<Invoice> invoices) {
        invoiceReadingFormService.uploadInvoiceList(invoices);
        return ResponseEntity.ok("Invoices Uploaded Successfully");
    }
}
