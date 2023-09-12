package com.gfstechnology.msvcstudent.resources;

import com.gfstechnology.msvcstudent.entities.Student;
import com.gfstechnology.msvcstudent.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentResource {

    private final StudentService service;

    @GetMapping
    public ResponseEntity<List<Student>> findAll(){
        return ResponseEntity.ok(service.findAllStudent());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findStudentById(id).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<Student> save(@RequestBody Student student){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveStudent(student));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Student> update(@RequestBody Student student, @PathVariable Long id){
        Optional<Student> studentOptional = service.findStudentById(id);

        if(studentOptional.isPresent()){

            Student studentUpdate = studentOptional.get();
            studentUpdate.setName(student.getName());
            studentUpdate.setEmail(student.getEmail());
            studentUpdate.setPassword(student.getPassword());

           return  ResponseEntity.status(HttpStatus.OK).body(service.saveStudent(studentUpdate));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Student> deleteById(@PathVariable Long id){
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
