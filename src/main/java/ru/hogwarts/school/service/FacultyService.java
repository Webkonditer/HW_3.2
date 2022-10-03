package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Set;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("The {} method was called.", "createFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        logger.debug("The {} method was called.", "getFacultyById");
        return facultyRepository.findById(facultyId).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.debug("The {} method was called.", "updateFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        logger.debug("The {} method was called.", "deleteFaculty");
        facultyRepository.deleteById(facultyId);
    }

    public List getFacultiesByColor(String facultyColor) {
        logger.debug("The {} method was called.", "getFacultiesByColor");
        return  facultyRepository.findByColor(facultyColor);
    }

    public List getFacultiesByNameOrColor(String name, String color) {
        logger.debug("The {} method was called.", "getFacultiesByNameOrColor");
        return  facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Set<Student> getFacultyStudents(Long facultyId) {
        logger.debug("The {} method was called.", "getFacultyStudents");
        try {
            Faculty sFaculty = facultyRepository.findById(facultyId).get();
            return sFaculty.getStudents();
        } catch(Exception e){
            return null;
        }
    }
}
