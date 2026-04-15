package com.mywallet.api;

import com.mywallet.api.dto.wallet.WalletDepositDto;
import com.mywallet.api.dto.wallet.WalletPaymentDto;
import com.mywallet.api.entity.Wallet;
import com.mywallet.api.exception.WalletNotFoundException;
import com.mywallet.api.repository.WalletRepository;
import com.mywallet.api.service.WalletService;
import com.mywallet.api.service.WalletTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletTransactionService walletTransactionService;

    @InjectMocks
    private WalletService walletService;

    @Test
    void testDepositWalletNotFound() {
        UUID uuid = UUID.randomUUID();
        when(walletRepository.findById(uuid)).thenReturn(Optional.empty());
        WalletDepositDto walletDepositDto = new WalletDepositDto(uuid,new BigDecimal(10));
        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(walletDepositDto));
    }

    @Test
    void testDepositSuccess() {
        UUID uuid = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setId(uuid);
        wallet.setBalance(new BigDecimal(100));
        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));

        WalletDepositDto walletDepositDto = new WalletDepositDto(uuid, new BigDecimal(50));
        walletService.deposit(walletDepositDto);

        assertEquals(new BigDecimal(150), wallet.getBalance());
        verify(walletRepository).save(wallet);
        verify(walletTransactionService).storeDepositWalletTransaction(wallet, walletDepositDto);
    }

    @Test
    void testPaymentSuccess() {
        UUID uuid = UUID.randomUUID();
        Wallet wallet = new Wallet();
        wallet.setId(uuid);
        wallet.setBalance(new BigDecimal(100));
        when(walletRepository.findById(uuid)).thenReturn(Optional.of(wallet));

        WalletPaymentDto walletPaymentDto = new WalletPaymentDto(uuid, new BigDecimal(30), "Test payment");
        walletService.payment(walletPaymentDto);

        assertEquals(new BigDecimal(70), wallet.getBalance());
        verify(walletRepository).save(wallet);
        verify(walletTransactionService).storePaymentWalletTransaction(wallet, walletPaymentDto);
    }

    @Test
    void testPaymentWalletNotFound() {
        UUID uuid = UUID.randomUUID();
        when(walletRepository.findById(uuid)).thenReturn(Optional.empty());
        WalletPaymentDto walletPaymentDto = new WalletPaymentDto(uuid, new BigDecimal(10), "Test");
        assertThrows(WalletNotFoundException.class, () -> walletService.payment(walletPaymentDto));
    }
}
