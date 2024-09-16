package com.icesi.edu.co.service;

import com.icesi.edu.co.model.Payment;
import com.icesi.edu.co.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    public PaymentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessPayment() {
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(100.0));

        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.processPayment(payment);

        assertNotNull(result);
        verify(paymentRepository, times(1)).save(payment);
    }
}
