package com.mywallet.api;

import com.mywallet.api.dto.wallet.WalletDepositDto;
import com.mywallet.api.exception.WalletNotFoundException;
import com.mywallet.api.repository.WalletRepository;
import com.mywallet.api.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @Test
    void testWalletNotFound() {
        UUID uuid = UUID.randomUUID();
        when(walletRepository.findById(uuid)).thenReturn(null);
        WalletDepositDto walletDepositDto = new WalletDepositDto(uuid,new BigDecimal(10));
        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(walletDepositDto));
    }
}
