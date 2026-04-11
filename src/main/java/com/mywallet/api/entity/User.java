package com.mywallet.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "password", nullable = false, updatable = true, length = 50)
    private String password;

    @Column(name = "email", nullable = false, updatable = true, length = 100)
    private String email;

    @Column(name = "username", nullable = false, updatable = true, length = 100)
    private String username;
}
