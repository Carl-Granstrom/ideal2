package com.ltu.ideal.model;


import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @GeneratedValue
    @Id
    @Column(name = "course_id", updatable = false, nullable = false)
    private Long id;

    @Basic
    @Column(name = "course_name")
    private String name;

    @Basic
    @Column(name = "course_code", unique = true)
    private String courseCode;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_fk")
    private Set<CourseInstance> courseInstances;

    @NotNull
    private LocalDate createdAt;

    public Course(String name, String courseCode, Set<CourseInstance> courseInstances){
        this.name = name;
        this.courseCode = courseCode;
        this.courseInstances = courseInstances;
    }

    // ********************** Accessor Methods ********************** //

    // ********************** Model Methods ********************** //

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDate.now();
    }


    // ********************** Common Methods ********************** //
}