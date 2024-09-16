package com.icesi.edu.co.controller;

import com.icesi.edu.co.model.Payment;
import com.icesi.edu.co.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        paymentService.handlePayment(payment);
        return ResponseEntity.ok(payment);
    }
}
