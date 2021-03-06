package com.ltu.ideal.controllers;

import com.ltu.ideal.model.Course;
import com.ltu.ideal.model.CourseInstance;
import com.ltu.ideal.model.Student;
import com.ltu.ideal.repository.CourseInstanceRepository;
import com.ltu.ideal.repository.CourseRepository;
import com.ltu.ideal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseInstanceRepository courseInstanceRepository;


    @GetMapping("/courses")
    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @PostMapping("/courses")
    public Course createCourse(@Valid @RequestBody Course course){
        return courseRepository.save(course);
    }

    @GetMapping("/courses/{id}")
    public Optional<Course> getCourseById(@PathVariable(value = "id") Long courseId){
        return courseRepository.findById(courseId);

    }

    @GetMapping("/courses/bycode/{courseCode}")
    public Course getCourseByCourseCode(@PathVariable(value = "courseCode") String courseCode){
        return courseRepository.findByCourseCode(courseCode);
    }

    //TODO Modify to not return null but instead a 404
    @GetMapping("/courses/bycode/{signupCode}/{ideal}")
    public Student getCourseAndStudents(
            @PathVariable(value = "signupCode") String signupCode,
            @PathVariable(value = "ideal") String ideal){

        CourseInstance courseInstance = courseInstanceRepository.findBySignupCode(signupCode);
        List<Student> studentList = courseInstance.getStudents();
        for (Student s : studentList) {
            if (s.getIdeal().equals(ideal)) {
                return s;
            }
        }
        return null;
    }

    //TODO Modify to not return null but instead a 404
    @GetMapping("/pnr/{signupCode}/{ideal}")
    public String getPnr(
            @PathVariable(value = "signupCode") String signupCode,
            @PathVariable(value = "ideal") String ideal){
        Optional<Student> optionalStudent;
        CourseInstance courseInstance = courseInstanceRepository.findBySignupCode(signupCode);
        List<Student> studentList = courseInstance.getStudents();
        for (Student s : studentList) {
            if (s.getIdeal().equals(ideal)) {
                return s.getPersonnummer();
            }
        }
        return null;
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable(value = "id") Long courseId){
        Optional<Course> c = courseRepository.findById(courseId);
        if (c.isPresent()){
            courseRepository.delete(c.get());
        }
    }

}
