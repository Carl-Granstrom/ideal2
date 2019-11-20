package com.ltu.ideal.test;

import com.ltu.ideal.model.Course;
import com.ltu.ideal.model.CourseInstance;
import com.ltu.ideal.model.Student;
import com.ltu.ideal.repository.CourseInstanceRepository;
import com.ltu.ideal.repository.CourseRepository;
import com.ltu.ideal.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Uses Spring test framework and junit 4 tests to add test data to the database and to test the REST-API.
 */
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
public class DBLoader {

    Long stud4id;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseInstanceRepository courseInstanceRepository;

    @Test
    public void testExampledata() {
        //Carl Granström uses a static ideal for easier testing
        Student student1 = studentRepository.save(
                new Student("Carl", "Granstrom", "198309290313", "cargra5"));
        //Students 2-4 uses randomly generated ideal numbers because... I can.
        Student student2 = studentRepository.save(
                new Student("Hulk", "Hogan", "200712220417"));
        Student student3 = studentRepository.save(
                new Student("Hannah", "Montana", "200003047222"));
        Student student4 = studentRepository.save(
                new Student("Gurra", "Grankott", "200001010101"));
        stud4id = student4.getId();
        System.out.println(stud4id);

        for (Student s : studentRepository.findAll()){
            System.out.println(s.getId());
        }

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        CourseInstance ci1 = new CourseInstance("LTU-12345", "HT18", students);
        courseInstanceRepository.save(ci1);

        List<Student> students2 = new ArrayList<>();
        students2.add(student1);
        students2.add(student2);
        students2.add(student3);
        students2.add(student4);
        CourseInstance ci2 = new CourseInstance("LTU-12346", "VT19", students);
        courseInstanceRepository.save(ci2);

        Set<CourseInstance> courseInstances = new HashSet<>();
        courseInstances.add(ci1);
        courseInstances.add(ci2);

        Course c1 = new Course("Databaser 2", "D0005N", courseInstances);
        courseRepository.save(c1);
        Course c2 = new Course("Data Mining", "D0025E", new HashSet<CourseInstance>());
        courseRepository.save(c2);

    }

}