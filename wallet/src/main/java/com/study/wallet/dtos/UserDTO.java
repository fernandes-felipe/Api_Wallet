package com.study.wallet.dtos;

import com.study.wallet.domain.user.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record UserDTO(
         Long id,
         String firstName,
         String lastName,
         String document,
         String email,
         String password,
         UserType userType,
         BigDecimal balance
) {
}
