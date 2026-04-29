package com.mywallet.api.service;

import com.mywallet.api.dto.wallet.WalletDepositDto;
import com.mywallet.api.dto.wallet.WalletPaymentDto;
import com.mywallet.api.entity.User;
import com.mywallet.api.entity.Wallet;
import com.mywallet.api.exception.WalletNotFoundException;
import com.mywallet.api.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    public WalletRepository walletRepository;
    public WalletTransactionService walletTransactionService;

    public WalletService(WalletRepository walletRepository, WalletTransactionService walletTransactionService) {
        this.walletRepository = walletRepository;
        this.walletTransactionService = walletTransactionService;
    }

    /**
     * Create a new Wallet for a given User with an initial balance of 0.
     * @param user User for whom the wallet will be created
     */
    public void createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal(0));
        wallet.setUser(user);
        walletRepository.save(wallet);
    }

    /**
     * Deposit a specified amount into the wallet identified by wallet_id. If the wallet is not found, a WalletNotFoundException is thrown.
     * @param walletDepositDto Data transfer object containing the wallet_id and the amount to be deposited
     */
    @Transactional
    public void deposit(WalletDepositDto walletDepositDto) {
        Wallet wallet = walletRepository.findById(walletDepositDto.wallet_id()).orElseThrow(WalletNotFoundException::new);
        wallet.setBalance(wallet.getBalance().add(walletDepositDto.amount()));
        walletRepository.save(wallet);
        walletTransactionService.storeDepositWalletTransaction(wallet, walletDepositDto);
    }

    /**
     * Process a payment by deducting a specified amount from the wallet identified by wallet_id. If the wallet is not found, a WalletNotFoundException is thrown.
     * @param walletPaymentDto Data transfer object containing the wallet_id and the amount to be paid
     */
    @Transactional
    public void payment(WalletPaymentDto walletPaymentDto) {
        Wallet wallet = walletRepository.findById(walletPaymentDto.wallet_id()).orElseThrow(WalletNotFoundException::new);
        wallet.setBalance(wallet.getBalance().subtract(walletPaymentDto.amount()));
        walletRepository.save(wallet);
        walletTransactionService.storePaymentWalletTransaction(wallet, walletPaymentDto);
    }
}
