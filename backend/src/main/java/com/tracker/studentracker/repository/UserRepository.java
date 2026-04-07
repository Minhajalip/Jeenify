package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.User;
import com.tracker.studentracker.models.Role;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAllByRoleIn(List<Role> roles);
}