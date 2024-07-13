package org.example.figma.repo;

import org.example.figma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "select u.* from users u join users_roles ur on u.id=ur.user_id\n" +
            "join roles r on ur.roles_id = r.id and r.role_name='ROLE_MANAGER'", nativeQuery = true)
    List<User> findAllManagers();

    User findByEmail(String email);
}