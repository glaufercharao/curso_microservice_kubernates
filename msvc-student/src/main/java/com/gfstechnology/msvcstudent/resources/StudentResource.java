package com.gfstechnology.msvcstudent.resources;

import com.gfstechnology.msvcstudent.entities.Student;
import com.gfstechnology.msvcstudent.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<?> save(@Valid @RequestBody Student student, BindingResult result){
        if( !student.getEmail().isBlank() && service.findStudentByEmail(student.getEmail()).isPresent()){
            return  ResponseEntity.badRequest().body(Collections.singletonMap("menssagem",
                    "E-mail já cadastrado"));
        }
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(validar(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveStudent(student));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Student student,BindingResult result, @PathVariable Long id){

        if(result.hasErrors()) {
            return ResponseEntity.badRequest().body(validar(result));
        }

        Optional<Student> studentOptional = service.findStudentById(id);

        if(studentOptional.isPresent()){
            Student studentUpdate = studentOptional.get();

            if(!student.getEmail().isBlank() &&
                    !student.getEmail().equals(studentUpdate.getEmail()) &&
                    service.findStudentByEmail(student.getEmail()).isPresent()){

                return  ResponseEntity.badRequest().body(Collections.
                        singletonMap("menssagem","E-mail já cadastrado"));
            }
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

    @GetMapping("/find-all-by-course")
    public ResponseEntity<?> findAllStudentByCourse(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.findByIds(ids));
    }

    private static Map<String, String> validar(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "O campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return errors;
    }
}
