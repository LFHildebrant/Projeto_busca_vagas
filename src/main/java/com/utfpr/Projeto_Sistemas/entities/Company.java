package com.utfpr.Projeto_Sistemas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
public class Company implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_company")
    private Integer idCompany;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Name can not be empty")
    @Size(min = 4, max = 150, message = "name must be between 4 and 150 characters")
    private String name;

    @Column(name = "business", nullable = false)
    @NotBlank(message = "business can not be empty")
    @Size(min = 4, max = 150, message = "business must be between 4 and 150 characters")
    private String business;

    @Column(name = "username", unique = true, nullable = false)
    @NotBlank(message = "username can not be empty")
    @Pattern(regexp = "^[0-9a-zA-Z]{3,20}$", message = "invalid username format.")
    private String username;

    @Column(name = "password", nullable = false)
    //@NotBlank(message = "password can not be empty")
    //@Pattern(regexp = "^[0-9a-zA-Z]{3,20}$", message = "invalid password format.")
    private String password;

    @Column(name = "email", nullable = true)
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "invalid email format")
    private String email;

    @Column(name = "phone", nullable = true)
    @Pattern(regexp = "^$|^(\\([0-9]{2}\\)|[0-9]{2})9?[0-9]{4}-?[0-9]{4}$", message = "invalid phone format")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @CreationTimestamp
    @Column(name = "creation_time_stamp")
    private Instant creationTimeStamp;

    @UpdateTimestamp
    @Column(name = "last_update_time_stamp")
    private Instant lastUpdateTimeStamp;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Company() {}

    public Company(String name, String username, String password, String email, String phone, String business, String city, String street, String state, String number, Instant creationTimeStamp, Instant lastUpdateTimeStamp, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.business = business;
        this.address = new Address(street, number, city, state);
        this.creationTimeStamp = creationTimeStamp;
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
