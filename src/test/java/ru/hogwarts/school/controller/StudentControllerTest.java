package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.servis.FacultyService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private Long testId = 9999L;


    @Test
    void contextLoads() throws Exception{
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void createStudentTest() throws JsonProcessingException {

        Student student = new Student();
        student.setId(testId);
        student.setName("Вася");
        student.setAge(22);

        ResponseEntity<String> response = restTemplate.postForEntity("/student", student, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Student responsedStudent = mapper.readValue(response.getBody(), Student.class);
        testId = responsedStudent.getId();

        String expected = "{\"id\":" + testId + ",\"name\":\"Вася\",\"age\":22}";

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(expected);

    }

    @Test
    void updateStudentTest()  throws JsonProcessingException {

        Student student = new Student();
        student.setId(1L);
        student.setName("Петя");
        student.setAge(999);

        HttpEntity<Student> entity = new HttpEntity<>(student);

        ResponseEntity<String> response = this.restTemplate.exchange("/student", HttpMethod.PUT, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Student responsedStudent = mapper.readValue(response.getBody(), Student.class);
        testId = responsedStudent.getId();

        String expected = "{\"id\":" + testId + ",\"name\":\"Петя\",\"age\":999}";

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(expected);

    }

    @Test
    void getUserTest() throws JsonProcessingException {
        this.updateStudentTest();
        ResponseEntity<String> response = restTemplate.getForEntity("/student/" + testId, String.class);

        String string = "{\"id\":" + testId + ",\"name\":\"Петя\",\"age\":999}";

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(string);
    }

    @Test
    void deleteStudent() throws JsonProcessingException {
        this.updateStudentTest();

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = this.restTemplate.exchange("/student/" + testId, HttpMethod.DELETE, entity, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getStudentsByAgeTest() throws JsonProcessingException {

        this.updateStudentTest();
        ResponseEntity<String> response = restTemplate.getForEntity("/student/age/999", String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getStudentsByAgeBetween() throws JsonProcessingException {

        this.updateStudentTest();
        ResponseEntity<String> response = restTemplate.getForEntity("/student/age/between?minAge=998&maxAge=1000", String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

     @Test
    void getStudentFaculty() {

         ResponseEntity<String> response = restTemplate.getForEntity("/student/2/faculty", String.class);
         String expected = "{\"id\":1,\"name\":\"f1\",\"color\":\"red\"}";

         Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
         Assertions.assertThat(response.getBody()).isEqualTo(expected);
    }

}