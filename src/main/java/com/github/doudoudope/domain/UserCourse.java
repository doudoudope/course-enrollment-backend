package com.github.doudoudope.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Entity
@Table (name= "user_course")
@Data
@NoArgsConstructor
public class UserCourse {
    // create 操作 enroll course
    public UserCourse (User user, Course course){
        this.user = user;
        this.course = course;
    }

    @Column(name = "id")
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne // 一对多
    private User user; // take user dependency

    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @ManyToOne // 一对多
    private Course course;
}
