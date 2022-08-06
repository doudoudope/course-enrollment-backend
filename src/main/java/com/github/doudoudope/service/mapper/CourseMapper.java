package com.github.doudoudope.service.mapper;

import com.github.doudoudope.domain.Course;
import com.github.doudoudope.service.dto.CourseDTO;
import org.springframework.stereotype.Service;

/**
 * Convert entity class Course to CourseDTO
 */
@Service
public class CourseMapper {

    public CourseDTO courseToCourseDTO(Course course) {
        return CourseDTO.builder()
            .courseName(course.getCourseName())
            .courseContent(course.getCourseContent())
            .courseLocation(course.getCourseLocation())
            .teacherId(course.getTeacherID())
            .build();
    }
}
