package com.gfstechnology.msvccourse.resources;

import com.gfstechnology.msvccourse.entities.Course;
import com.gfstechnology.msvccourse.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Course> save(@RequestBody Course course){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveCourse(course));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Course> update(@RequestBody Course course, @PathVariable Long id){
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
}
