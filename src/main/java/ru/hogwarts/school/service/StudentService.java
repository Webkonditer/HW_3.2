package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

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

    public List<String> getStudentsByLetter(String letter) {
        return studentRepository.findAll().stream()
                                .map(s -> s.getName().toLowerCase())
                                .filter(n -> n.startsWith(letter.toLowerCase()))
                                .map(String::toUpperCase)
                                .sorted()
                                .collect(Collectors.toList());
    }

    public double getAverageStudentsAge() {
        return studentRepository.findAll().stream()
                                .mapToInt(Student::getAge)
                                .average().orElse(0);
    }

    public String mathFunction() {
        long start = System.currentTimeMillis();
        Integer rezult = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        long executionTime = System.currentTimeMillis() - start;

        long start2 = System.currentTimeMillis();
        Integer rezult2 = Stream.iterate(1, a -> a +1) .parallel() .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        long executionTime2 = System.currentTimeMillis() - start2;

        return "Ответ: " + rezult + " Время без запараллеливания: " + executionTime  + " , Время с запараллеливанием: " + executionTime2;

    }
}
