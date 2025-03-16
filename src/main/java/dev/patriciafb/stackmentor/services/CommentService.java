package dev.patriciafb.stackmentor.services;



import dev.patriciafb.stackmentor.models.Comment;
 import dev.patriciafb.stackmentor.models.Resource;
 import dev.patriciafb.stackmentor.models.User;
 import dev.patriciafb.stackmentor.repositories.CommentRepository;
 import dev.patriciafb.stackmentor.repositories.ResourceRepository;
 import dev.patriciafb.stackmentor.repositories.UserRepository;
 import org.springframework.stereotype.Service;
 
 import org.hibernate.Hibernate;


 
 import java.util.List;
 import java.util.Optional;
 
 @Service
 public class CommentService {
     
     private final CommentRepository commentRepository;
     private final ResourceRepository resourceRepository;
     private final UserRepository userRepository;
 
    public CommentService(CommentRepository commentRepository, ResourceRepository resourceRepository,
            UserRepository userRepository) {
         this.commentRepository = commentRepository;
         this.resourceRepository = resourceRepository;
         this.userRepository = userRepository;
     }
 
     
     public List<Comment> getCommentsByResource(Long resourceId) {
        List<Comment> comments = commentRepository.findByResourceId(resourceId);

      
        comments.forEach(comment -> {
            Hibernate.initialize(comment.getUser()); 
        });

        return comments;
     }
 
   
     public Optional<Comment> addComment(Long resourceId, Long userId, String content) {
         Optional<Resource> resourceOpt = resourceRepository.findById(resourceId);
         Optional<User> userOpt = userRepository.findById(userId);
     
         if (resourceOpt.isEmpty() || userOpt.isEmpty()) {
             return Optional.empty(); 
         }
     
         Comment comment = new Comment();
         comment.setContent(content);
         comment.setResource(resourceOpt.get()); 
         comment.setUser(userOpt.get()); 
     
         return Optional.of(commentRepository.save(comment));
     }
     
     
     
 
     public Optional<Comment> updateComment(Long commentId, String userEmail, String newContent) {
         Optional<Comment> existingComment = commentRepository.findById(commentId);
 
         if (existingComment.isPresent() && existingComment.get().getUser().getEmail().equals(userEmail)) {
             existingComment.get().setContent(newContent);
            commentRepository.save(existingComment.get());
            return Optional.of(existingComment.get()); 
         }
         return Optional.empty();
     }
 
     
     public boolean deleteComment(Long commentId, String userEmail) {
         Optional<Comment> existingComment = commentRepository.findById(commentId);
         
         if (existingComment.isPresent() && existingComment.get().getUser().getEmail().equals(userEmail)) {
             commentRepository.deleteById(commentId);
             return true;
         }
         return false;
     }
 }