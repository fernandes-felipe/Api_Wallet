package com.study.wallet.dtos;

import java.math.BigDecimal;

public record TransactionDTO(
        BigDecimal value,
        Long senderId,
        Long receiverId
) {
}
