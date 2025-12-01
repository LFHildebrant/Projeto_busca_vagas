package com.utfpr.Projeto_Sistemas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "applications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application")
    private Integer idApplication;

    @ManyToOne
    @JoinColumn(name ="job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name ="user_id",  nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    private String email;

    private String  phone;

    @Column(name = "education", nullable = false)
    @Size(max = 600)
    String education;

    @Column(name = "experience", nullable = false)
    @Size(max = 600)
    String experience;

    @CreationTimestamp
    @Column(name = "creation_time_stamp")
    private Instant creationTimeStamp;

    private String feedback;

    public Application(Job job, User user, String name, String education, String experience, Instant creationTimeStamp) {
        this.job = job;
        this.user = user;
        this.name = name;
        this.education = education;
        this.experience = experience;
        this.creationTimeStamp = creationTimeStamp;
    }

    public Application(Job job, User user, String name, String email, String phone, String experience, String education, Instant creationTimeStamp) {
        this.job = job;
        this.user = user;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.experience = experience;
        this.education = education;
        this.creationTimeStamp = creationTimeStamp;
    }
}
