package com.gfstechnology.msvccourse.models.entities;

import com.gfstechnology.msvccourse.models.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tb_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe o nome")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<CourseStudent> courseStudent = new ArrayList<>();

    @Transient
    private List<Student> students = new ArrayList<>();

    public void addCourseStudent(CourseStudent courseStudent){
        this.courseStudent.add(courseStudent);
    }

    public void removeCourseStudent(CourseStudent courseStudent){
        this.courseStudent.remove(courseStudent);
    }
}
