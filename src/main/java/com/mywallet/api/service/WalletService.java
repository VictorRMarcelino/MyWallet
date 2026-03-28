package com.mywallet.api.service;

import com.mywallet.api.dto.WalletDepositDto;
import com.mywallet.api.dto.WalletTransferDto;
import com.mywallet.api.entity.Wallet;
import com.mywallet.api.exception.DestinyWalletNotFound;
import com.mywallet.api.exception.NotEnoughBalanceException;
import com.mywallet.api.exception.WalletNotFoundException;
import com.mywallet.api.repository.WalletRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class WalletService {

    public WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void deposit(WalletDepositDto walletDepositDto) {
        Wallet wallet = walletRepository.findById(walletDepositDto.id_wallet()).orElseThrow(WalletNotFoundException::new);
        wallet.setBalance(wallet.getBalance().add(walletDepositDto.amount()));
        walletRepository.save(wallet);
    }

    @Transactional
    public void transfer(WalletTransferDto walletTransferDto) {
        Wallet wallet = walletRepository.findById(walletTransferDto.id_wallet()).orElseThrow(WalletNotFoundException::new);
        Wallet destinyWallet = walletRepository.findById(walletTransferDto.id_wallet_destiny()).orElseThrow(DestinyWalletNotFound::new);

        if (wallet.getBalance().subtract(walletTransferDto.amount()).compareTo(new BigDecimal(0)) < 0) {
            throw new NotEnoughBalanceException();
        }

        destinyWallet.setBalance(destinyWallet.getBalance().add(walletTransferDto.amount()));
        wallet.setBalance(wallet.getBalance().add(walletTransferDto.amount()));
        walletRepository.save(destinyWallet);
        walletRepository.save(wallet);
    }
}
