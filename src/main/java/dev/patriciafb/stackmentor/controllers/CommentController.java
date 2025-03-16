package dev.patriciafb.stackmentor.controllers;

import dev.patriciafb.stackmentor.models.Comment;
import dev.patriciafb.stackmentor.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<Map<String, Object>>> getComments(@PathVariable Long resourceId) {
        List<Comment> comments = commentService.getCommentsByResource(resourceId);

        List<Map<String, Object>> response = comments.stream().map(comment -> Map.of(
                "id", comment.getId(),
                "content", comment.getContent(),
                "user", comment.getUser() != null ? Map.of(
                        "id", comment.getUser().getId(),
                        "name", comment.getUser().getUsername()) : null))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody Map<String, String> requestData) {
        System.out.println("📩 Datos recibidos: " + requestData);

        if (!requestData.containsKey("resourceId") || !requestData.containsKey("userId")
                || !requestData.containsKey("content")) {
            return ResponseEntity.badRequest().body("❌ Faltan datos en la solicitud.");
        }

        try {
            Long resourceId = Long.parseLong(requestData.get("resourceId"));
            Long userId = Long.parseLong(requestData.get("userId"));
            String content = requestData.get("content");

            Optional<Comment> savedComment = commentService.addComment(resourceId, userId, content);

            return savedComment.isPresent()
                    ? ResponseEntity.ok(savedComment.get())
                    : ResponseEntity.badRequest().body("No se pudo agregar el comentario.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error procesando la solicitud: " + e.getMessage());
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestParam String userEmail,
            @RequestParam String newContent) {
        Optional<Comment> comment = commentService.updateComment(commentId, userEmail, newContent);
        return comment.isPresent() ? ResponseEntity.ok(comment.get())
                : ResponseEntity.badRequest().body("No autorizado para editar este comentario.");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @RequestParam String userEmail) {
        return commentService.deleteComment(commentId, userEmail) ? ResponseEntity.ok("Comentario eliminado.")
                : ResponseEntity.badRequest().body("No autorizado para eliminar este comentario.");
    }
}