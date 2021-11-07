package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gbteam.lms.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "select * from users as u left join course_users as cu on cu.users_id=u.id where cu.courses_id=:course_id")
    List<User> findByCourse(@Param("course_id") Long courseId);

    @Query(nativeQuery = true, value = "select * from users where users.id not in \n" +
            "(select id from users as u\n" +
            "join course_users as cu on cu.users_id=u.id where cu.courses_id=:course_id)")
    List<User> findByCourseNotEqual(@Param("course_id") Long courseId);
}
