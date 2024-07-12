package org.example.figma.repo;

import org.example.figma.entity.Role;
import org.example.figma.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "select * from roles where role_name=? limit 1",nativeQuery = true)
    Role findByRoleName(String roleName);
}