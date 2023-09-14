package com.gfstechnology.msvccourse.services.impl;

import com.gfstechnology.msvccourse.client.StudentClientRest;
import com.gfstechnology.msvccourse.models.Student;
import com.gfstechnology.msvccourse.models.entities.Course;
import com.gfstechnology.msvccourse.models.entities.CourseStudent;
import com.gfstechnology.msvccourse.repositories.CourseRespository;
import com.gfstechnology.msvccourse.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRespository repository;
    private  final StudentClientRest clientRest;
    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllCourse() {
        return (List<Course>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findCourseAndStudentsByIds(Long id) {
        Optional<Course> course = repository.findById(id);

        if(course.isPresent()){
            if(!course.get().getCourseStudent().isEmpty()){
                List<Long> ids = course.get().getCourseStudent().stream()
                        .map(CourseStudent::getStudentId).toList();
                List<Student> students = clientRest.findAllStudentByCourse(ids);
                course.get().setStudents(students);
            }
            return course;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        return repository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Optional<Course> oCourse = findCourseById(id);
        oCourse.orElseThrow(() -> new RuntimeException("Course not found "));
        repository.deleteById(oCourse.get().getId());
    }

    @Override
    @Transactional
    public void removeCourseStudentById(Long id) {
        repository.removeCourseStudentById(id);
    }

    @Override
    @Transactional
    public Optional<Student> associateStudent(Student student, Long courseId) {
        Optional<Course> optionalCourse = repository.findById(courseId);
         if(optionalCourse.isPresent()){
             Student studentClient = clientRest.findStudentById(student.getId());

             CourseStudent courseStudent = new CourseStudent();
             courseStudent.setStudentId(studentClient.getId());

             optionalCourse.get().addCourseStudent(courseStudent);
             repository.save(optionalCourse.get());
             return Optional.of(studentClient);
         }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Student> createStudent(Student student, Long courseId) {
        Optional<Course> optionalCourse = repository.findById(courseId);
        if(optionalCourse.isPresent()){
            Student studentClient = clientRest.saveStudent(student);

            CourseStudent courseStudent = new CourseStudent();
            courseStudent.setStudentId(studentClient.getId());

            optionalCourse.get().addCourseStudent(courseStudent);
            repository.save(optionalCourse.get());
            return Optional.of(studentClient);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Student> disassociateStudent(Student student, Long courseId) {
        Optional<Course> optionalCourse = repository.findById(courseId);
        if(optionalCourse.isPresent()){
            Student studentClient = clientRest.findStudentById(student.getId());

            CourseStudent courseStudent = new CourseStudent();
            courseStudent.setStudentId(studentClient.getId());

            optionalCourse.get().removeCourseStudent(courseStudent);
            repository.save(optionalCourse.get());
            return Optional.of(studentClient);
        }

        return Optional.empty();
    }
}
