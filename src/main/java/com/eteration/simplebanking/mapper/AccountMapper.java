package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.dto.AccountCreateRequest;
import com.eteration.simplebanking.dto.AccountInfoResponse;
import com.eteration.simplebanking.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountCreateRequest convertToAccountCreateRequest(Account account);

    Account convertToAccount(AccountCreateRequest accountCreateRequest);

    @Mapping(target = "transactions", source = "transactions")
    AccountInfoResponse toAccountInfoResponse(Account account);


}
