package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.dto.PayRequest;
import com.eteration.simplebanking.entity.BillPaymentTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface BillPaymentTransactionMapper {
    PayRequest convertToPayRequest(BillPaymentTransaction billPaymentTransaction);

    BillPaymentTransaction convertToBillPaymentTransaction(PayRequest payRequest);

}
