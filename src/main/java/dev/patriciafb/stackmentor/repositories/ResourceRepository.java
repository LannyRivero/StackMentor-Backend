package dev.patriciafb.stackmentor.repositories;

import dev.patriciafb.stackmentor.models.Resource;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByCategory(String category);
}
