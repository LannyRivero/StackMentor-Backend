package dev.patriciafb.stackmentor.service;



import dev.patriciafb.stackmentor.model.Comment;
import dev.patriciafb.stackmentor.model.Resource;
import dev.patriciafb.stackmentor.model.User;
import dev.patriciafb.stackmentor.repository.CommentRepository;
import dev.patriciafb.stackmentor.repository.ResourceRepository;
import dev.patriciafb.stackmentor.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ResourceRepository resourceRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    // 🟢 Obtener comentarios de un recurso
    public List<Comment> getCommentsByResource(Long resourceId) {
        return commentRepository.findByResourceId(resourceId);
    }

    // 🟢 Agregar un comentario
    public Optional<Comment> addComment(Long resourceId, Long userId, String content) {
        Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
        Optional<User> userOpt = userRepository.findById(userId);
    
        if (resourceOpt.isEmpty() || userOpt.isEmpty()) {
            return Optional.empty(); // ❌ Retorna vacío si no encuentra el recurso o usuario
        }
    
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setResource(resourceOpt.get()); // ✅ Ahora funciona porque agregamos setResource()
        comment.setUser(userOpt.get()); // ✅ Ahora funciona porque agregamos setUser()
    
        return Optional.of(commentRepository.save(comment));
    }
    
    
    

    // 🟠 Editar un comentario (solo si es del usuario)
    public Optional<Comment> updateComment(Long commentId, String userEmail, String newContent) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);

        if (existingComment.isPresent() && existingComment.get().getUser().getEmail().equals(userEmail)) {
            existingComment.get().setContent(newContent);
            return Optional.of(commentRepository.save(existingComment.get()));
        }
        return Optional.empty();
    }

    // 🔴 Eliminar un comentario (solo si es del usuario)
    public boolean deleteComment(Long commentId, String userEmail) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        
        if (existingComment.isPresent() && existingComment.get().getUser().getEmail().equals(userEmail)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }
}

