package com.github.doudoudope.service;

import com.github.doudoudope.domain.Course;
import com.github.doudoudope.domain.User;
import com.github.doudoudope.domain.UserCourse;
import com.github.doudoudope.repository.CourseRepository;
import com.github.doudoudope.repository.UserCourseRepository;
import com.github.doudoudope.repository.UserRepository;
import com.github.doudoudope.service.dto.CourseDTO;
import com.github.doudoudope.service.mapper.CourseMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {
    private UserRepository userRepository;
    private CourseRepository courseRepository;

    private UserCourseRepository userCourseRepository;

    private CourseMapper courseMapper;

    /**
     * 1. check user exist? -> get User(based on userName)
     * 2. check course exist> -> get Course
     * 3. new UserCourse(User, Course)
     * 4. remove this UserCourse
     * @param userName
     * @param courseName
     * @return
     */
    public void dropCourse(String userName, String courseName) {
        // step 123
        UserCourse userCourse = getUserCourse(userName, courseName);
        // remove this UserCourse
        userCourseRepository.deleteByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
    }

    public List<CourseDTO> listAllCourses() {
        List<Course> courses = courseRepository.findAll();
        // data transfer object access control
//        List<CourseDTO> courseDTOList = new ArrayList<>();
//        for (Course course: courses) {
//            courseDTOList.add(courseMapper.courseToCourseDTO(course));
//        }
//        return courseDTOList;
        return courses.stream().map(course -> courseMapper.courseToCourseDTO(course)).collect(Collectors.toList());
    }
    /**
     * 1. check user exist? -> get User(based on userName)
     * 2. check course exist> -> get Course
     * 3. new UserCourse(User, Course)
     * 4. check if usercourse not exist
     * 4. save this new UserCourse
     * @param userName
     * @param courseName
     * @return
     */
    public void enrollCourse(String userName, String courseName) {
        // step 123
        UserCourse userCourse = getUserCourse(userName, courseName);
        // 4. check if UserCourse not exist
        Optional<UserCourse> optionalUserCourse = userCourseRepository.findOneByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
        optionalUserCourse.ifPresent(existingUserCourse -> {
            throw new IllegalArgumentException("UserCourse already exists " + existingUserCourse.toString());
        });
        // 5. save this new UserCourse
        userCourseRepository.save(userCourse);

    }

    /**
     * 1. check user exist? -> get User(based on userName)
     * 2. Get List<UserCourse> based on user
     * 3. Get List<Course> based on user
     * 4. remove this UserCourse
     * @param userName
     * @return
     */
    public List<CourseDTO> getEnrolledCourses(String userName) {
        // 1. Check user exist based on userName> -> get User
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user:" + userName));
        // 2. Get List<UserCourse> based on user
        List<UserCourse> allByUser = userCourseRepository.findAllByUser(user);

        return allByUser.stream()
            .map(userCourse -> userCourse.getCourse())
            .map(course -> courseMapper.courseToCourseDTO(course))
            .collect(Collectors.toList());
    }

    private UserCourse getUserCourse(String userName, String courseName) {
        // 1. Check user exist based on userName> -> get User
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user:" + userName));
        // 2.
        Optional<Course> optionalCourse = courseRepository.findOneByCourseName(courseName);
        Course course = optionalCourse.orElseThrow(() -> new IllegalArgumentException("No such course: " + courseName));
        //3. new UserCourse
        return new UserCourse(user, course);

    }
}
