package dev.patriciafb.stackmentor.repositories;


import dev.patriciafb.stackmentor.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 🟢 Obtener todos los comentarios de un recurso específico
    List<Comment> findByResourceId(Long resourceId);

    // 🟠 Eliminar comentarios por ID del usuario (Opcional, si quieres borrar en cascada)
    void deleteByUserId(Long userId);
}
