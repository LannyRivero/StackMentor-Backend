package dev.patriciafb.stackmentor.services;

import dev.patriciafb.stackmentor.models.Comment;
import dev.patriciafb.stackmentor.models.Resource;
import dev.patriciafb.stackmentor.models.User;
import dev.patriciafb.stackmentor.repositories.CommentRepository;
import dev.patriciafb.stackmentor.repositories.ResourceRepository;
import dev.patriciafb.stackmentor.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentsByResource_Success() {

        Resource mockResource = new Resource("Test Resource", "Description", "FRONTEND", "LIBRERÍAS", "LINK",
                "https://example.com/resource", null, null, null, null);
        User mockUser = new User("test", "test@example.com", "password123");

        List<Comment> mockComments = Arrays.asList(
                new Comment("Comment 1", mockResource, mockUser),
                new Comment("Comment 2", mockResource, mockUser));

        when(commentRepository.findByResourceId(1L)).thenReturn(mockComments);

        List<Comment> comments = commentService.getCommentsByResource(1L);

        assertNotNull(comments);
        assertEquals(2, comments.size());
        assertEquals("Comment 1", comments.get(0).getContent());
        assertEquals("Comment 2", comments.get(1).getContent());

        verify(commentRepository, times(1)).findByResourceId(1L);
    }

    @Test
    void testGetCommentsByResource_NoResourceFound() {

        when(commentRepository.findByResourceId(999L)).thenReturn(java.util.Collections.emptyList());

        List<Comment> comments = commentService.getCommentsByResource(999L);

        assertNotNull(comments);
        assertTrue(comments.isEmpty());

        verify(commentRepository, times(1)).findByResourceId(999L);
    }

    @Test
    void testAddComment_Success() {

        Resource mockResource = new Resource("Test Resource", "Description", "FRONTEND", "LIBRERÍAS", "LINK",
                "https://example.com/resource", null, null, null, null);
        User mockUser = new User("test", "test@example.com", "password123");

        when(resourceRepository.findById(1L)).thenReturn(Optional.of(mockResource));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);
            savedComment.setId(1L);
            return savedComment;
        });

        Optional<Comment> savedComment = commentService.addComment(1L, 1L, "This is a test comment");

        assertTrue(savedComment.isPresent());
        assertEquals("This is a test comment", savedComment.get().getContent());
        assertEquals(mockResource, savedComment.get().getResource());
        assertEquals(mockUser, savedComment.get().getUser());

        verify(resourceRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testAddComment_UserNotFound() {

        Resource mockResource = new Resource("Test Resource", "Description", "FRONTEND", "LIBRERÍAS", "LINK",
                "https://example.com/resource", null, null, null, null);
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(mockResource));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Comment> savedComment = commentService.addComment(1L, 999L, "This is a test comment");

        assertFalse(savedComment.isPresent());

        verify(resourceRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(999L);
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void testUpdateComment_CommentNotFound() {

        when(commentRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Comment> updatedComment = commentService.updateComment(999L, "test@example.com", "Updated content");

        assertFalse(updatedComment.isPresent());

        verify(commentRepository, times(1)).findById(999L);
        verify(userRepository, never()).findByEmail(anyString());
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void testDeleteComment_CommentNotFound() {
        when(commentRepository.findById(999L)).thenReturn(Optional.empty());

        boolean result = commentService.deleteComment(999L, "test@example.com");

        assertFalse(result);

        verify(commentRepository, times(1)).findById(999L);
        verify(userRepository, never()).findByEmail(anyString());
        verify(commentRepository, never()).deleteById(anyLong());
    }

}
