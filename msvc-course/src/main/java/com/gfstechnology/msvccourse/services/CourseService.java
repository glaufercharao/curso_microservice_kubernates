package com.gfstechnology.msvccourse.services;

import com.gfstechnology.msvccourse.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAllCourse();
    Optional<Course> findCourseById(Long id);
    Course saveCourse(Course course);
    void deleteCourse(Long id);
}
