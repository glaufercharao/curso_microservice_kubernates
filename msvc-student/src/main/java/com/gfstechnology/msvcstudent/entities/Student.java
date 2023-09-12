package com.gfstechnology.msvcstudent.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
}
