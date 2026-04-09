package com.mywallet.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Table (name = "wallet")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(name = "balance", nullable = false)
    public BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
}
