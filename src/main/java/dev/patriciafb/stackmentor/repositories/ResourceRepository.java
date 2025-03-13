package dev.patriciafb.stackmentor.repositories;

import dev.patriciafb.stackmentor.models.Resource;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByCategory(String category);
    List<Resource> findByCategoryAndSubcategory(String category, String subcategory);
    Optional<Resource> findByFileName(String fileName);
}
