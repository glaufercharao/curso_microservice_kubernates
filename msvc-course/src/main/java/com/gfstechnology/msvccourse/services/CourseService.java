package com.gfstechnology.msvccourse.services;

import com.gfstechnology.msvccourse.models.Student;
import com.gfstechnology.msvccourse.models.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAllCourse();
    Optional<Course> findCourseById(Long id);
    Optional<Course> findCourseAndStudentsByIds(Long id);
    Course saveCourse(Course course);
    void deleteCourse(Long id);
    void removeCourseStudentById(Long id);

    Optional<Student> associateStudent(Student student, Long courseId);
    Optional<Student> createStudent(Student student, Long courseId);
    Optional<Student> disassociateStudent(Student student, Long courseId);
}
