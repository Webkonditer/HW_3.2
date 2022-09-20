package ru.hogwarts.school.servis;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long facultyId) {
        return facultyRepository.findById(facultyId).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    public List getFacultiesByColor(String facultyColor) {
        return  facultyRepository.findByColor(facultyColor);
    }

    public List getFacultiesByNameOrColor(String name, String color) {
        return  facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }

    public Set<Student> getFaciltyStudents(Long facultyId) {
        Faculty sFaculty = facultyRepository.findById(facultyId).get();
        return sFaculty.getStudents();
    }

}
