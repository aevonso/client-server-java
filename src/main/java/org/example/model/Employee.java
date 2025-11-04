package org.example.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String position; //мне впадлу приводить к 3НФ, поэтому сделать стрингом, ну как-нибудь допилю FK

    private String email;
    private String phone;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
