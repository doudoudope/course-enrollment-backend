package com.github.doudoudope.web.rest;

import com.github.doudoudope.security.SecurityUtils;
import com.github.doudoudope.service.CourseService;
import com.github.doudoudope.service.dto.CourseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CourseController {
    private CourseService courseService;
    //Dependency injection
    //Lombok setter/getter/constructor
//    public CourseController(CourseService courseService) {
//        this.courseService = courseService;
//    }

    /**
     * 1. API requirement: 列出所有已有课程
     * 2. http method: GET
     * 3. URL: /allCourses
     * 4. HTTP status code: 200
     * 5. request body：None
     * 6. Response body: List<CourseDTO> data transfer object 数据库里的数据被map成一个java class 一行数据对应一个java object
     * // 数据库里面每一列都对应一个class
     * // user table对应user class。为什么不直接把list of user 直接传回给前端呢？
     * // user class -> user table
     * // user dto: 只expose id 和login 数据 权限保护
     * 7. request header: JWT token (当且仅当学生登陆了)
     */
    @GetMapping(path = "/allCourses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CourseDTO> listAllCourses() {
        return courseService.listAllCourses();
    }

    /**
     * 1. API requirement: 实现学生选课功能 -> Create relationship between a student and a course
     * 2. http method: POST
     * 3. URL: /student/course/{courseName}
     * 4. HTTP status code: 200
     * 5. request body：前端提供给后端额外的信息 courseName (PathVariable)
     * 6. Response body: None
     * 7. request header: JWT token (当且仅当学生登陆了)
     */
    @PostMapping(path = "/student/course/{courseName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void enrollCourse(@PathVariable String courseName) {
        // call CourseService to do the real logic
        // 1. somehow get username from JWT token
        String userName = getUserName();
        // 2. drop course with username and coursename
        courseService.enrollCourse(userName, courseName);
    }

    /**
     * 1. API requirement: 列出学生已选课程
     * 2. http method:GET
     * 3. URL: /student/courses
     * 4. HTTP status code: 200
     * 5. request body：None
     * 6. Response body: <CourseDTO>
     * 7. request header: JWT token (当且仅当学生登陆了)
     */
    @GetMapping(path = "/student/courses")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CourseDTO> enrollCourses() {
        String userName = getUserName();
        return courseService.getEnrolledCourses(userName);
    }

    /**
     * 1. API requirement: 实现学生删课 operation (增删改，不会返回任何信息)
     * 2. http method: DELETE
     * 3. URL: /student/course/{courseName}
     * 4. HTTP status code: 200, 204 (没有response body）
     * 5. request body：前端提供给后端额外的信息 courseName (PathVariable)
     * 6. Response body: None
     * 7. request header: JWT token (当且仅当学生登陆了)
     */
    @DeleteMapping(path = "/student/course/{courseName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dropCourse(@PathVariable String courseName) {
        // call CourseService to do the real logic
        // 1. somehow get username from JWT token
        String userName = getUserName();
        // 2. drop course with username and coursename
        courseService.dropCourse(userName, courseName);

    }

    /**
     * Extract username from JET token
     * @return username(String)
     */
    private String getUserName() {
        // optional<> -> 盒子 必须主动把盒子打开，把string拿出来。
        // 很多情况下，程序员老是忘记做null check
        // getCurrentUserLogin().get() // null pointer exception
        return SecurityUtils.getCurrentUserLogin().orElseThrow(() ->{
            throw new UsernameNotFoundException("username not found");
        });
    }
}
