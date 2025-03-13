package dev.patriciafb.stackmentor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.patriciafb.stackmentor.model.Resource;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByCategory(String category);
    List<Resource> findByCategoryAndSubcategory(String category, String subcategory);
    Optional<Resource> findByFileName(String fileName);
   
}



