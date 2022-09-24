package ru.hogwarts.school.servis;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        try {
            return studentRepository.findById(studentId).get();
        } catch(Exception e){
            return null;
        }

    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public List getStudentsByAge(int studentAge) {
       return  studentRepository.findByAge(studentAge);
    }

    public List getStudentByAgeBetween(int minAge, int maxAge) {
       return  studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }

    public Faculty getStudentFacilty(Long studentId) {
        try {
            Student student = studentRepository.findById(studentId).get();
            return student.getFaculty();
        } catch(Exception e){
            return null;
        }
    }
}
