package dev.patriciafb.stackmentor.services;

import dev.patriciafb.stackmentor.models.Resource;
import dev.patriciafb.stackmentor.repositories.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetResourceByFileName_Success() {
        
        Resource mockResource = new Resource("Resource 2", "Desc 2", "BACKEND", "FRAMEWORKS", "FILE", null, new byte[]{1, 2, 3}, "application/pdf", "file.pdf", null);

        when(resourceRepository.findByFileName("file.pdf")).thenReturn(Optional.of(mockResource));

        Optional<Resource> resourceOptional = resourceService.getResourceByFileName("file.pdf");

        assertTrue(resourceOptional.isPresent());
        assertEquals("file.pdf", resourceOptional.get().getFileName());
        assertEquals("application/pdf", resourceOptional.get().getFileType());

        verify(resourceRepository, times(1)).findByFileName("file.pdf");
    }

    @Test
    void testGetResourceByFileName_NotFound() {
        
        when(resourceRepository.findByFileName("unknown.pdf")).thenReturn(Optional.empty());

        Optional<Resource> resourceOptional = resourceService.getResourceByFileName("unknown.pdf");

        assertFalse(resourceOptional.isPresent());

        verify(resourceRepository, times(1)).findByFileName("unknown.pdf");
    }

    @Test
    void testGetResourceById_Success() {
        
        Resource mockResource = new Resource("Resource 1", "Desc 1", "FRONTEND", "LIBRERÍAS", "LINK", "https://example.com/resource1", null, null, null, null);
        mockResource.setId(1L);
       
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(mockResource));

        Optional<Resource> resourceOptional = resourceService.getResourceById(1L);

        assertTrue(resourceOptional.isPresent());
        assertEquals("Resource 1", resourceOptional.get().getTitle());
        assertEquals("LINK", resourceOptional.get().getType());

        verify(resourceRepository, times(1)).findById(1L);
    }

    @Test
    void testGetResourceById_NotFound() {
       
        when(resourceRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Resource> resourceOptional = resourceService.getResourceById(999L);

        assertFalse(resourceOptional.isPresent());

        verify(resourceRepository, times(1)).findById(999L);
    }

    @Test
    void testGetResources_Success() {
      
        List<Resource> mockResources = Arrays.asList(
                new Resource("Resource 1", "Desc 1", "FRONTEND", "LIBRERÍAS", "LINK", "https://example.com/resource1", null, null, null, null),
                new Resource("Resource 2", "Desc 2", "BACKEND", "FRAMEWORKS", "FILE", null, new byte[]{1, 2, 3}, "application/pdf", "file.pdf", null)
        );

      
        when(resourceRepository.findByCategoryAndSubcategory("BACKEND", "FRAMEWORKS"))
                .thenReturn(mockResources.subList(1, 2)); 

        List<Resource> resources = resourceService.getResources("BACKEND", "FRAMEWORKS");

        assertNotNull(resources);
        assertEquals(1, resources.size());
        assertEquals("Resource 2", resources.get(0).getTitle());
        assertEquals("FILE", resources.get(0).getType());

        verify(resourceRepository, times(1)).findByCategoryAndSubcategory("BACKEND", "FRAMEWORKS");
    }

    @Test
    void testSaveResource() throws IOException {
       
        String title = "Spring Boot Guide";
        String description = "PDF guide for Spring Boot";
        String category = "BACKEND";
        String subcategory = "FRAMEWORKS";
        String url = null; 
        MultipartFile file = new MockMultipartFile("file", "guide.pdf", "application/pdf", "file content".getBytes());

        when(resourceRepository.save(any(Resource.class))).thenAnswer(invocation -> {
            Resource savedResource = invocation.getArgument(0);
            savedResource.setId(1L); 
            return savedResource;
        });

        Resource savedResource = resourceService.saveResource(title, description, category, subcategory, url, file);

        assertNotNull(savedResource);
        assertEquals("Spring Boot Guide", savedResource.getTitle());
        assertEquals("BACKEND", savedResource.getCategory());
        assertEquals("FRAMEWORKS", savedResource.getSubcategory());
        assertNull(savedResource.getUrl()); 
        assertEquals("guide.pdf", savedResource.getFileName());
        assertEquals("application/pdf", savedResource.getFileType());
        assertArrayEquals("file content".getBytes(), savedResource.getFileData());

        verify(resourceRepository, times(1)).save(any(Resource.class));
    }
}