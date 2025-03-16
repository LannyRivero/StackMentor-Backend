package dev.patriciafb.stackmentor.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private Comment comment;
    private Resource mockResource;
    private User mockUser;

    @BeforeEach
    void setUp() {

        mockResource = new Resource("Test Resource", "Description", "FRONTEND", "LIBRERÍAS", "LINK",
                "https://example.com/resource", null, null, null, null);
        mockUser = new User("test", "test@example.com", "password123");

        // Crear un comentario con constructor parametrizado
        comment = new Comment("This is a test comment", mockResource, mockUser);
    }

    @Test
    void testDefaultConstructor() {
        Comment defaultComment = new Comment();
        assertNotNull(defaultComment);
        assertNull(defaultComment.getId());
        assertNull(defaultComment.getContent());
        assertNotNull(defaultComment.getCreatedAt());
        assertNull(defaultComment.getResource());
        assertNull(defaultComment.getUser());
    }

    @Test
    void testParameterizedConstructor() {
        assertNotNull(comment);
        assertEquals("This is a test comment", comment.getContent());
        assertNotNull(comment.getCreatedAt());
        assertEquals(mockResource, comment.getResource());
        assertEquals(mockUser, comment.getUser());
    }

    @Test
    void testGettersAndSetters() {

        comment.setId(1L);
        assertEquals(1L, comment.getId());

        comment.setContent("Updated comment content");
        assertEquals("Updated comment content", comment.getContent());

        LocalDateTime customDate = LocalDateTime.of(2023, 10, 10, 12, 0, 0);
        comment.setCreatedAt(customDate);
        assertEquals(customDate, comment.getCreatedAt());

        Resource newResource = new Resource("New Resource", "Description", "BACKEND", "FRAMEWORKS", "FILE", null,
                "file.pdf".getBytes(), "application/pdf", "file.pdf", null);
        comment.setResource(newResource);
        assertEquals(newResource, comment.getResource());

        User newUser = new User("newuser", "newuser@example.com", "newpassword123");
        comment.setUser(newUser);
        assertEquals(newUser, comment.getUser());
    }

    @Test
    void testResourceRelation() {
        assertNotNull(comment.getResource());
        assertEquals("Test Resource", comment.getResource().getTitle());
    }

    @Test
    void testUserRelation() {
        assertNotNull(comment.getUser());
        assertEquals("test", comment.getUser().getUsername());
    }

    @Test
    void testCreatedAtAutoGeneration() {
        Comment newComment = new Comment("Auto-generated date test", mockResource, mockUser);
        assertNotNull(newComment.getCreatedAt());
    }
}