package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final AvatarService avatarService;

    public StudentService(StudentRepository studentRepository, AvatarService avatarService) {
        this.studentRepository = studentRepository;
        this.avatarService = avatarService;
    }

    public Student createStudent(Student student) {
        logger.debug("The {} method was called.", "createStudent");
        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        logger.debug("The getStudentById method was called.");
        try {
            return studentRepository.findById(studentId).get();
        } catch(Exception e){
            return null;
        }

    }

    public Student updateStudent(Student student) {
        logger.debug("The updateStudent method was called.");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        logger.debug("The deleteStudent method was called.");
        avatarService.deleteAvatar(studentId);
        studentRepository.deleteById(studentId);
    }

    public List getStudentsByAge(int studentAge) {
        logger.debug("The getStudentsByAge method was called.");
        return  studentRepository.findByAge(studentAge);
    }

    public List getStudentByAgeBetween(int minAge, int maxAge) {
        logger.debug("The getStudentByAgeBetween method was called.");
        return  studentRepository.findStudentByAgeBetween(minAge, maxAge);
    }

    public Faculty getStudentFacilty(Long studentId) {
        logger.debug("The getStudentFacilty method was called.");
        try {
            Student student = studentRepository.findById(studentId).get();
            return student.getFaculty();
        } catch(Exception e){
            return null;
        }
    }

    public Long getStudentsCount() {
        logger.debug("The getStudentsCount method was called.");
        return  studentRepository.getStudentsCount();
    }

    public int getStudentsaverageAge() {
        logger.debug("The getStudentsaverageAge method was called.");
        return  studentRepository.getStudentsAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.debug("The getLastFiveStudents method was called.");
        return  studentRepository.getLastFiveStudents();
    }
}
