package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
