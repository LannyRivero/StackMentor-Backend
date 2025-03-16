package dev.patriciafb.stackmentor.controllers;

import dev.patriciafb.stackmentor.models.Comment;
import dev.patriciafb.stackmentor.models.User;
import dev.patriciafb.stackmentor.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings("unused")
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommentsByResource_NoCommentsFound() {

        when(commentService.getCommentsByResource(999L)).thenReturn(java.util.Collections.emptyList());

        ResponseEntity<List<Map<String, Object>>> response = commentController.getComments(999L);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(commentService, times(1)).getCommentsByResource(999L);
    }

    @Test
    void testAddComment_Success() {

        Map<String, String> requestData = Map.of(
                "resourceId", "1",
                "userId", "1",
                "content", "This is a test comment");

        Comment mockComment = new Comment("This is a test comment", null, null);
        when(commentService.addComment(eq(1L), eq(1L), eq("This is a test comment")))
                .thenReturn(Optional.of(mockComment));

        ResponseEntity<?> response = commentController.addComment(requestData);

        assertNotNull(response.getBody());
        assertEquals("This is a test comment", ((Comment) response.getBody()).getContent());

        verify(commentService, times(1)).addComment(eq(1L), eq(1L), eq("This is a test comment"));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testAddComment_BadRequest() {
        Map<String, String> requestData = Map.of(
                "resourceId", "1",
                "content", "This is a test comment");

        ResponseEntity<?> response = commentController.addComment(requestData);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("❌ Faltan datos en la solicitud.", response.getBody());

        verify(commentService, never()).addComment(anyLong(), anyLong(), anyString());
    }

    @Test
    void testUpdateComment_Success() {

        Comment mockComment = new Comment("Old content", null, null);
        when(commentService.updateComment(eq(1L), eq("test@example.com"), eq("Updated content")))
                .thenReturn(Optional.of(mockComment));

        ResponseEntity<?> response = commentController.updateComment(1L, "test@example.com", "Updated content");

        assertNotNull(response.getBody());
        assertEquals("Old content", ((Comment) response.getBody()).getContent());

        verify(commentService, times(1)).updateComment(eq(1L), eq("test@example.com"), eq("Updated content"));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testUpdateComment_CommentNotFound() {

        when(commentService.updateComment(eq(999L), eq("test@example.com"), eq("Updated content")))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = commentController.updateComment(999L, "test@example.com", "Updated content");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("No autorizado para editar este comentario.", response.getBody());

        verify(commentService, times(1)).updateComment(eq(999L), eq("test@example.com"), eq("Updated content"));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testDeleteComment_Success() {

        when(commentService.deleteComment(eq(1L), eq("test@example.com"))).thenReturn(true);

        ResponseEntity<?> response = commentController.deleteComment(1L, "test@example.com");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Comentario eliminado.", response.getBody());

        verify(commentService, times(1)).deleteComment(eq(1L), eq("test@example.com"));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testDeleteComment_Unauthorized() {

        when(commentService.deleteComment(eq(1L), eq("anotheruser@example.com"))).thenReturn(false);

        ResponseEntity<?> response = commentController.deleteComment(1L, "anotheruser@example.com");

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("No autorizado para eliminar este comentario.", response.getBody());

        verify(commentService, times(1)).deleteComment(eq(1L), eq("anotheruser@example.com"));
    }
}