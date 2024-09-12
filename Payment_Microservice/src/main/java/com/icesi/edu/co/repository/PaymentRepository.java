package com.icesi.edu.co.repository;

import com.icesi.edu.co.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
