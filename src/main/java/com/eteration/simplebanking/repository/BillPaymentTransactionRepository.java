package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.BillPaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillPaymentTransactionRepository extends JpaRepository<BillPaymentTransaction, Long> {
}
