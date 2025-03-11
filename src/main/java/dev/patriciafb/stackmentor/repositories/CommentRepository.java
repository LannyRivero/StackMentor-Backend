package dev.patriciafb.stackmentor.repositories;

import dev.patriciafb.stackmentor.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByResourceId(Long resourceId);
}
