package com.gfstechnology.msvccourse.client;

import com.gfstechnology.msvccourse.models.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-student", url = "msvc-student:8081/students" )
public interface StudentClientRest {

    @GetMapping("/{id}")
    Student findStudentById(@PathVariable Long id);

    @PostMapping
    Student saveStudent(@RequestBody Student student);

    @GetMapping("/find-all-by-course")
    List<Student> findAllStudentByCourse(@RequestParam Iterable<Long> ids);
}
