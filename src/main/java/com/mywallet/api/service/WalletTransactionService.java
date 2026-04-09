package com.mywallet.api.service;

import com.mywallet.api.dto.wallet.WalletDepositDto;
import com.mywallet.api.dto.wallet.WalletPaymentDto;
import com.mywallet.api.entity.Wallet;
import com.mywallet.api.entity.WalletTransaction;
import com.mywallet.api.enums.WalletTransactionEnum;
import com.mywallet.api.repository.WalletTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletTransactionService {

    public WalletTransactionRepository walletTransactionRepository;

    WalletTransactionService(WalletTransactionRepository walletTransactionRepository) {
        this.walletTransactionRepository = walletTransactionRepository;
    }

    public void storeDepositWalletTransaction(Wallet wallet, WalletDepositDto walletDepositDto) {
        String description = String.format("%f deposited with sucess!", walletDepositDto.amount());
        this.storeWalletTransaction(wallet, WalletTransactionEnum.TYPE_DEPOSIT.value, description);
    }

    public void storePaymentWalletTransaction(Wallet wallet, WalletPaymentDto walletPaymentDto) {
        String description = String.format("%f payed with sucess!");
        this.storeWalletTransaction(wallet, WalletTransactionEnum.TYPE_PAYMENT.value, description);
    }

    private void storeWalletTransaction(Wallet wallet, int type, String description) {
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setType(type);
        walletTransaction.setDescription(description);
        walletTransactionRepository.save(walletTransaction);
    }
}
