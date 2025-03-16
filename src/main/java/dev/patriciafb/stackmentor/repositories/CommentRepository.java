package dev.patriciafb.stackmentor.repositories;


import dev.patriciafb.stackmentor.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByResourceId(Long resourceId);

    void deleteByUserId(Long userId);
}
