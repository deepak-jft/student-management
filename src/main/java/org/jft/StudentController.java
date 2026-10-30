
package org.jft;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return repository.save(student);
    }

    @GetMapping
    public List<Student> getAll() {
        return repository.findAll();
    }

//    @GetMapping("/{id}")
//    public Student getById(@PathVariable UUID id) {
//        try {
//            return repository.findById(id).orElseThrow(
//                    () -> new RuntimeException("Student not found: " + id)
//            );
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return repository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Student not found: " + id));
    }

    @GetMapping("/email/{email}")
    public Student getByEmail(@PathVariable String email) {
        return repository.getStudentByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with email: " + email
                        ));
    }


    @PutMapping("/{id}")
    public Student update(
            @PathVariable UUID id,
            @RequestBody Student updatedStudent) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found: " + id));

        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());

        return repository.save(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repository.deleteById(id);
    }
}
