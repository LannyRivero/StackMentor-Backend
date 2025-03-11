package dev.patriciafb.stackmentor.repositories;

import dev.patriciafb.stackmentor.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
   // Optional<User> findByEmail(String email);
}
