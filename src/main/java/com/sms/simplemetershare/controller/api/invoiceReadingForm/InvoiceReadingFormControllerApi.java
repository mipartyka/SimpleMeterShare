package com.sms.simplemetershare.controller.api.invoiceReadingForm;

import com.sms.simplemetershare.entity.Invoice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/v1/invoice-form")
public interface InvoiceReadingFormControllerApi {
    @PostMapping
    ResponseEntity<String> uploadInvoiceForm(@RequestBody List<Invoice> invoices);
}
