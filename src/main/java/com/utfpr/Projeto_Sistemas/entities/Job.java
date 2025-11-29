package com.utfpr.Projeto_Sistemas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "jobs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_job")
    private int idJob;

    @ManyToOne
    @JoinColumn(name ="company_id")
    private Company company;

    @NotBlank(message = "Title can not be empty")
    @Size(min = 3, max = 150)
    private String title;

    @NotNull(message = "Area can not be empty")
    @Enumerated(EnumType.STRING)
    private AreaJob area;

    @NotBlank(message = "Description can not be empty")
    @Size(min = 10, max = 5000)
    private String description;

    @NotBlank(message = "State can not be empty")
    private String state;

    @NotBlank(message = "City can not be empty")
    private String city;

    private BigDecimal salary;

    @CreationTimestamp
    @Column(name = "creation_time_stamp")
    private Instant creationTimeStamp;

    @UpdateTimestamp
    @Column(name = "last_update_time_stamp")
    private Instant lastUpdateTimeStamp;

    public Job(Company company, String title, AreaJob area, String description, String state, String city, BigDecimal salary, Instant creationTimeStamp, Instant lastUpdateTimeStamp) {
        this.company = company;
        this.title = title;
        this.area = area;
        this.description = description;
        this.state = state;
        this.city = city;
        this.salary = salary;
        this.creationTimeStamp = creationTimeStamp;
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }
}
