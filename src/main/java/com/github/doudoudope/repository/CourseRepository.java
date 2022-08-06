package com.github.doudoudope.repository;

import com.github.doudoudope.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course/*ORM model class*/, Long/**/> {
    Optional<Course> findOneByCourseName(String courseName);

}
