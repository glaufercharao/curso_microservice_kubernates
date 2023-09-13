package com.gfstechnology.msvcstudent.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "tb_students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Informe o nome")
    private String name;

    @Column(unique = true)
    @Email
    @NotBlank(message = "Informe o E-mail")
    private String email;
    @NotBlank(message = "Informe uma senha")
    private String password;
}
