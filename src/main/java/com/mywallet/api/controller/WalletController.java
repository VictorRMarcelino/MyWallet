package com.mywallet.api.controller;

import com.mywallet.api.dto.ResponseDto;
import com.mywallet.api.dto.wallet.WalletDepositDto;
import com.mywallet.api.dto.wallet.WalletPaymentDto;
import com.mywallet.api.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    public WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation( summary = "Deposit a value into a wallet",
                description = "Deposit an amount of money into a wallet",
                tags = "Deposit")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deposit realized with success!"),
        @ApiResponse(responseCode = "404", description = "Wallet not found"),
    })
    @PutMapping("/deposit")
    public ResponseEntity<ResponseDto> deposit(@Valid @RequestBody WalletDepositDto walletDepositDto) {
        this.walletService.deposit(walletDepositDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
            HttpStatus.OK.value(),
            "Deposit realized with success!"
        ));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment realized with success!"),
        @ApiResponse(responseCode = "404", description = "Wallet not found"),
        @ApiResponse(responseCode = "400", description = "Not enough balance")
    })
    @PostMapping("/payment")
    public ResponseEntity<ResponseDto> payment(@Valid @RequestBody WalletPaymentDto walletPaymentDto) {
        walletService.payment(walletPaymentDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
            HttpStatus.OK.value(),
            "Payment realized with success!"
        ));
    }
}
