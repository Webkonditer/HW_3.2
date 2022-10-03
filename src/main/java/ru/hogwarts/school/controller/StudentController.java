package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RequestMapping("student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{studentId}")
    public ResponseEntity getUser(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if(student == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public ResponseEntity updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("age/{studentAge}")
    public List getStudentsByAge(@PathVariable int studentAge) {
        return studentService.getStudentsByAge(studentAge);
    }

    @GetMapping("age/between")
    public List getStudentsByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.getStudentByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{studentId}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long studentId) {
        return studentService.getStudentFacilty(studentId);
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getStudentsCount() {
        return ResponseEntity.ok(studentService.getStudentsCount());
    }

    @GetMapping("/averageAge")
    public ResponseEntity<Integer> getStudentsaverageAge() {
        return ResponseEntity.ok(studentService.getStudentsaverageAge());
    }
    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

}
