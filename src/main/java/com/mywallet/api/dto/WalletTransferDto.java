package com.mywallet.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record WalletTransferDto (
    @Schema(
        description = "Wallet ID",
        example = "1"
    )
    @NotNull
    Long id_wallet,

    @Schema(
        description = "Destiny Wallet ID",
        example = "2"
    )
    @NotNull
    Long id_wallet_destiny,

    @Schema(
        description = "Amount transfered",
        example = "10.05"
    )
    @NotNull
    @PositiveOrZero
    BigDecimal amount
){ }
