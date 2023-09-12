package com.gfstechnology.msvccourse.services.impl;

import com.gfstechnology.msvccourse.entities.Course;
import com.gfstechnology.msvccourse.repositories.CourseRespository;
import com.gfstechnology.msvccourse.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRespository respository;
    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllCourse() {
        return (List<Course>) respository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseById(Long id) {
        return respository.findById(id);
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        return respository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Optional<Course> oCourse = findCourseById(id);
        oCourse.orElseThrow(() -> new RuntimeException("Course not found "));
        respository.deleteById(oCourse.get().getId());
    }
}
