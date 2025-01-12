package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.dto.TransactionResponse;
import com.eteration.simplebanking.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponse toTransactionResponse(Transaction transaction);
}
