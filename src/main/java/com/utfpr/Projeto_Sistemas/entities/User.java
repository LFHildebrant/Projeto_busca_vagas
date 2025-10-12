package com.utfpr.Projeto_Sistemas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user")
    private UUID idUser;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 4, max = 150)
    private String name;

    @Column(name = "username", unique = true, nullable = false)
    @NotNull
    @Pattern(regexp = "^[0-9a-zA-Z]{3,20}$")
    private String username;

    @Column(name = "password", nullable = false)
    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^([0-9a-zA-Z]{3,20})$")
    private String password;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "phone", nullable = true)
    @Pattern(regexp = "^(([1-9]{2})|[1-9]{2})9?[1-9]{4}-?[1-9]{4}$")
    private String phone;

    @Column(name = "experience", nullable = true)
    private String experience;

    @Column(name = "education", nullable = true)
    private String education;

    @CreationTimestamp
    @Column(name = "creation_time_stamp")
    private Instant creationTimeStamp;

    @UpdateTimestamp
    @Column(name = "last_update_time_stamp")
    private Instant lastUpdateTimeStamp;

    public User() {
    }

    public User(UUID idUser, String name, String username, String password, String email, String phone, String experience, String education) {
        this.idUser = idUser;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.experience = experience;
        this.education = education;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Instant getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Instant creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public Instant getLastUpdateTimeStamp() {
        return lastUpdateTimeStamp;
    }

    public void setLastUpdateTimeStamp(Instant lastUpdateTimeStamp) {
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }
}
