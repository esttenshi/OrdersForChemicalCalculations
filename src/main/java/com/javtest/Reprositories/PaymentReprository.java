package com.javtest.Reprositories;

import com.javtest.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentReprository extends JpaRepository<Payment, Integer> {
}