package com.gfstechnology.msvccourse.resources;

import com.gfstechnology.msvccourse.models.Student;
import com.gfstechnology.msvccourse.models.entities.Course;
import com.gfstechnology.msvccourse.services.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseResource {

    public final CourseService service;

    @GetMapping
    public ResponseEntity<List<Course>> findAll(){
        return ResponseEntity.ok(service.findAllCourse());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findCourseAndStudentsByIds(id).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(validar(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveCourse(course));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id){

        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(validar(result));
        }

        Optional<Course> courseOptional = service.findCourseById(id);

        if(courseOptional.isPresent()){

            Course courseUpdate = courseOptional.get();
            courseUpdate.setName(course.getName());

            return  ResponseEntity.status(HttpStatus.OK).body(service.saveCourse(courseUpdate));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Course> deleteById(@PathVariable Long id){
        service.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove-student/{id}")
    public ResponseEntity<?> removeCourseStudentById(@PathVariable Long id){
        service.removeCourseStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/associate-student/{courseId}")
    public ResponseEntity<?> associateStudent(@RequestBody Student student, @PathVariable Long courseId) {
        Optional<Student> studentAssociate;

        try {
            studentAssociate = service.associateStudent(student, courseId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Erro: ",
                    "Falha na comunicação entre serviços: "+ e.getMessage()));
        }

        if(studentAssociate.isPresent()){
            return  ResponseEntity.status(HttpStatus.CREATED).body(studentAssociate.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save-student/{courseId}")
    public ResponseEntity<?> saveStudent(@RequestBody Student student, @PathVariable Long courseId) {
        Optional<Student> studentAssociate;

        try {
            studentAssociate = service.createStudent(student, courseId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Erro: ",
                    "Falha na comunicação entre serviços: "+ e.getMessage()));
        }

        if(studentAssociate.isPresent()){
            return  ResponseEntity.status(HttpStatus.CREATED).body(studentAssociate.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/disassociate-student/{courseId}")
    public ResponseEntity<?> disassociateStudent(@RequestBody Student student, @PathVariable Long courseId) {
        Optional<Student> studentAssociate;

        try {
            studentAssociate = service.disassociateStudent(student, courseId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Erro: ",
                    "Falha na comunicação entre serviços"));
        }

        if(studentAssociate.isPresent()){
            return  ResponseEntity.status(HttpStatus.OK).body(studentAssociate.get());
        }

        return ResponseEntity.notFound().build();
    }

    private static Map<String, String> validar(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "O campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return errors;
    }
}
