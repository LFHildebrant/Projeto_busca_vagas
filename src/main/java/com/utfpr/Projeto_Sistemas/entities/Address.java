package com.utfpr.Projeto_Sistemas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Integer idUser;

    @NotBlank(message = "street can not be empty")
    @Size(min = 3, max = 150, message = "street must be between 3 and 150 characters")
    private String street;

    @NotBlank(message = "number can not be null")
    @Pattern(regexp = "^[0-9]{1,8}$", message = "invalid number format. Must be a positive number")
    private String number;

    @NotBlank(message = "city can not be empty")
    @Size(min = 3, max = 150, message = "city must be between 3 and 150 characters")
    private String city;

    @NotBlank(message = "state can not be empty")
    private String state;

    public Address() {
    }

    public Address(String street, String number, String city, String state) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
    }
}
