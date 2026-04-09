package com.mywallet.api.dto.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

public record WalletDepositDto (
    @Schema(
        description = "Wallet ID",
        example = "1"
    )
    @NotNull
    UUID wallet_id,

    @Schema(
        description = "Amount deposited",
        example = "10.05"
    )
    @NotNull
    @PositiveOrZero
    BigDecimal amount
){}
