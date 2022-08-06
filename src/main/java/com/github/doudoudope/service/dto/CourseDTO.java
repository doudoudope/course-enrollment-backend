package com.github.doudoudope.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
// constructor input parameter >= 4
@Builder
public class CourseDTO {
    private String courseName;
    private String courseContent;
    private String courseLocation;
    private Long teacherId;

}
