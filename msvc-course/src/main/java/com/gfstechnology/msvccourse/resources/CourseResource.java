package com.gfstechnology.msvccourse.resources;

import com.gfstechnology.msvccourse.entities.Course;
import com.gfstechnology.msvccourse.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return ResponseEntity.ok(service.findCourseById(id).orElseThrow());
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

    private static Map<String, String> validar(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "O campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return errors;
    }
}
