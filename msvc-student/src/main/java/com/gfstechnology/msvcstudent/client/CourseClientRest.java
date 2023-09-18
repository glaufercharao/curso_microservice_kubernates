package com.gfstechnology.msvcstudent.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-course", url = "msvc-course:8082/courses")
public interface CourseClientRest {

    @DeleteMapping("/remove-student/{id}")
    void removeCourseStudentById(@PathVariable Long id);
}
