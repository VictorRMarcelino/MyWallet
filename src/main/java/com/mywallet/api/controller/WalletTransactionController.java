package com.mywallet.api.controller;

import com.mywallet.api.entity.WalletTransaction;
import com.mywallet.api.service.WalletTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallettransactions")
public class WalletTransactionController {

    private final WalletTransactionService walletTransactionService;

    public WalletTransactionController(WalletTransactionService walletTransactionService) {
        this.walletTransactionService = walletTransactionService;
    }

    @Operation(summary = "Get last 5 transactions for a wallet",
               description = "Retrieve the last 5 wallet transactions for the given wallet ID",
               tags = "Wallet Transactions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    @GetMapping("/lastTransactions/{walletId}")
    public ResponseEntity<List<WalletTransaction>> getLastTransactions(@PathVariable UUID walletId) {
        List<WalletTransaction> transactions = walletTransactionService.getLastTransactions(walletId);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
}
