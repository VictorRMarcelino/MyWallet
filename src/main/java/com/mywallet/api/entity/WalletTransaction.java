package com.mywallet.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallettransaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    Wallet wallet_id;

    @Column(name = "type", nullable = false)
    int type;

    @Column(name = "description", length = 100)
    String description;

    @Column(name = "created_at", nullable = false, length = 20)
    String created_at;
}
