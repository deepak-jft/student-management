
package org.jft;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository
        extends JpaRepository<Student, UUID> {

    // custom function for get user by email
    @Query(value = "SELECT * FROM student WHERE email = :email LIMIT 1",
            nativeQuery = true)
    Optional<Student> getStudentByEmail(@Param("email") String email);
}
