package com.mywallet.api.entity;

import com.mywallet.api.enums.WalletTransactionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "wallettransaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet_id;

    @Column(name = "type", nullable = false, columnDefinition = "smallint")
    private WalletTransactionEnum type;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "created_at", nullable = false, length = 20)
    private OffsetDateTime created_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
