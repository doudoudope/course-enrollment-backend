package com.github.doudoudope.repository;

import com.github.doudoudope.domain.Course;
import com.github.doudoudope.domain.User;
import com.github.doudoudope.domain.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    Optional<UserCourse> findOneByUserAndCourse(User user, Course course);

    @Transactional // 增删改 事物 强一致性
    void deleteByUserAndCourse(User user, Course course);

    List<UserCourse> findAllByUser(User user);

}
