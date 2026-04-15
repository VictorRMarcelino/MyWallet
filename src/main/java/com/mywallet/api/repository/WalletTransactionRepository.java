package com.mywallet.api.repository;

import com.mywallet.api.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, UUID> {

    @Query("SELECT wt FROM WalletTransaction wt WHERE wt.wallet_id.id = :walletId ORDER BY wt.created_at DESC LIMIT 5")
    List<WalletTransaction> getWalletLastTransactions(UUID walletId);
}
