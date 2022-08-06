package com.github.doudoudope.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "course")
@Data // getter + setter
public class Course {
    @Column(name = "id")
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;
    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_location")
    private String courseLocation;
    @Column(name = "course_content")
    private String courseContent;

    @Column(name = "teacher_id")
    private Long teacherID;


}
