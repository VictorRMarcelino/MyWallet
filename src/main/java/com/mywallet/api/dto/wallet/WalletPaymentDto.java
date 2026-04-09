package com.mywallet.api.dto.wallet;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletPaymentDto (
    UUID wallet_id,
    BigDecimal amount,
    String description
){}
