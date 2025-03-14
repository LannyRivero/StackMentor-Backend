package dev.patriciafb.stackmentor.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    private Resource resource;

    @BeforeEach
    void setUp() {
        
        User mockUploader = new User("test", "test@example.com", "password123");
        resource = new Resource(
                "Test Resource",
                "This is a test resource",
                "FRONTEND",
                "LIBRERÍAS",
                "LINK",
                "https://example.com/resource",
                null,
                null,
                null,
                mockUploader
        );
    }

   
    @Test
    void testDefaultConstructor() {
        Resource defaultResource = new Resource();
        assertNotNull(defaultResource);
        assertNull(defaultResource.getId());
        assertNull(defaultResource.getTitle());
        assertNull(defaultResource.getDescription());
        assertNull(defaultResource.getCategory());
        assertNull(defaultResource.getSubcategory());
        assertNull(defaultResource.getUrl());
        assertNull(defaultResource.getFileData());
        assertNull(defaultResource.getFileType());
        assertNull(defaultResource.getFileName());
        assertNull(defaultResource.getType());
        assertNull(defaultResource.getUploader());
        assertNull(defaultResource.getComments());
    }

   
    @Test
    void testParameterizedConstructor() {
        assertNotNull(resource);
        assertEquals("Test Resource", resource.getTitle());
        assertEquals("This is a test resource", resource.getDescription());
        assertEquals("FRONTEND", resource.getCategory());
        assertEquals("LIBRERÍAS", resource.getSubcategory());
        assertEquals("LINK", resource.getType());
        assertEquals("https://example.com/resource", resource.getUrl());
        assertNull(resource.getFileData());
        assertNull(resource.getFileType());
        assertNull(resource.getFileName());
        assertNotNull(resource.getUploader());
    }

   
    @Test
    void testGettersAndSetters() {
        resource.setId(1L);
        resource.setTitle("New Title");
        resource.setDescription("New Description");
        resource.setCategory("BACKEND");
        resource.setSubcategory("FRAMEWORKS");
        resource.setType("FILE");
        resource.setUrl(null); 
        resource.setFileData(new byte[]{1, 2, 3});
        resource.setFileType("application/pdf");
        resource.setFileName("file.pdf");

        assertEquals(1L, resource.getId());
        assertEquals("New Title", resource.getTitle());
        assertEquals("New Description", resource.getDescription());
        assertEquals("BACKEND", resource.getCategory());
        assertEquals("FRAMEWORKS", resource.getSubcategory());
        assertEquals("FILE", resource.getType());
        assertNull(resource.getUrl()); 
        assertArrayEquals(new byte[]{1, 2, 3}, resource.getFileData());
        assertEquals("application/pdf", resource.getFileType());
        assertEquals("file.pdf", resource.getFileName());
    }

   
    @Test
    void testUploaderRelation() {
        User mockUploader = new User("test", "test@example.com", "password123");
        resource.setUploader(mockUploader);

        assertNotNull(resource.getUploader());
        assertEquals("test@example.com", resource.getUploader().getEmail());
    }

    
    @Test
    void testCommentsRelation() {
        List<Comment> mockComments = new ArrayList<>();
        Comment comment1 = new Comment("Great resource!", null, null);
        Comment comment2 = new Comment("Very helpful!", null, null);

        mockComments.add(comment1);
        mockComments.add(comment2);

        resource.setComments(mockComments);

        assertNotNull(resource.getComments());
        assertEquals(2, resource.getComments().size());

        for (Comment comment : resource.getComments()) {
            assertNotNull(comment.getContent());
        }
    }

    
    @Test
    void testToString() {
        resource.setId(1L);
        resource.setTitle("Test Resource");
        resource.setDescription("This is a test resource");
        resource.setCategory("FRONTEND");
        resource.setSubcategory("LIBRERÍAS");
        resource.setUrl("https://example.com/resource");
        resource.setFileType("text/html");
        resource.setFileName("resource.html");

        String expected = "Resource{id=1, title='Test Resource', description='This is a test resource', category='FRONTEND', subcategory='LIBRERÍAS', url='https://example.com/resource', fileType='text/html', fileName='resource.html'}";
        assertEquals(expected, resource.toString());
    }
}