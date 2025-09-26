package com.ra.shop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", length = 100)
    private String fullName;
    @Column(name = "email", length = 150, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phone", length = 20, unique = true)
    private String phone;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
