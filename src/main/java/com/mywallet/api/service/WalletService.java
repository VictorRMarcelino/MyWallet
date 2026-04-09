package com.mywallet.api.service;

import com.mywallet.api.dto.wallet.WalletDepositDto;
import com.mywallet.api.dto.wallet.WalletPaymentDto;
import com.mywallet.api.entity.Wallet;
import com.mywallet.api.exception.WalletNotFoundException;
import com.mywallet.api.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    public WalletRepository walletRepository;
    public WalletTransactionService walletTransactionService;

    public WalletService(WalletRepository walletRepository, WalletTransactionService walletTransactionService) {
        this.walletRepository = walletRepository;
        this.walletTransactionService = walletTransactionService;
    }

    public void deposit(WalletDepositDto walletDepositDto) {
        Wallet wallet = walletRepository.findById(walletDepositDto.wallet_id()).orElseThrow(WalletNotFoundException::new);
        wallet.setBalance(wallet.getBalance().add(walletDepositDto.amount()));
        walletRepository.save(wallet);
        walletTransactionService.storeDepositWalletTransaction(wallet, walletDepositDto);
    }

    public void payment(WalletPaymentDto walletPaymentDto) {
        Wallet wallet = walletRepository.findById(walletPaymentDto.wallet_id()).orElseThrow(WalletNotFoundException::new);
        wallet.setBalance(wallet.getBalance().subtract(walletPaymentDto.amount()));
        walletRepository.save(wallet);
        walletTransactionService.storePaymentWalletTransaction(wallet, walletPaymentDto);
    }
}
