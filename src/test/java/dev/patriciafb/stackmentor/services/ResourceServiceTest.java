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

    /**
     * Test para obtener un recurso por nombre de archivo (existe).
     */
    @Test
    void testGetResourceByFileName_Success() {
        // Preparar datos de prueba
        Resource mockResource = new Resource("Resource 2", "Desc 2", "BACKEND", "FRAMEWORKS", "FILE", null, new byte[]{1, 2, 3}, "application/pdf", "file.pdf", null);

        // Configurar el comportamiento del repositorio
        when(resourceRepository.findByFileName("file.pdf")).thenReturn(Optional.of(mockResource));

        // Ejecutar el método
        Optional<Resource> resourceOptional = resourceService.getResourceByFileName("file.pdf");

        // Verificar el resultado
        assertTrue(resourceOptional.isPresent());
        assertEquals("file.pdf", resourceOptional.get().getFileName());
        assertEquals("application/pdf", resourceOptional.get().getFileType());

        // Verificar interacciones con el repositorio
        verify(resourceRepository, times(1)).findByFileName("file.pdf");
    }

    /**
     * Test para obtener un recurso por nombre de archivo (no existe).
     */
    @Test
    void testGetResourceByFileName_NotFound() {
        // Configurar el comportamiento del repositorio
        when(resourceRepository.findByFileName("unknown.pdf")).thenReturn(Optional.empty());

        // Ejecutar el método
        Optional<Resource> resourceOptional = resourceService.getResourceByFileName("unknown.pdf");

        // Verificar el resultado
        assertFalse(resourceOptional.isPresent());

        // Verificar interacciones con el repositorio
        verify(resourceRepository, times(1)).findByFileName("unknown.pdf");
    }

    /**
     * Test para obtener un recurso por ID (existe).
     */
    @Test
    void testGetResourceById_Success() {
        // Preparar datos de prueba
        Resource mockResource = new Resource("Resource 1", "Desc 1", "FRONTEND", "LIBRERÍAS", "LINK", "https://example.com/resource1", null, null, null, null);
        mockResource.setId(1L);

        // Configurar el comportamiento del repositorio
        when(resourceRepository.findById(1L)).thenReturn(Optional.of(mockResource));

        // Ejecutar el método
        Optional<Resource> resourceOptional = resourceService.getResourceById(1L);

        // Verificar el resultado
        assertTrue(resourceOptional.isPresent());
        assertEquals("Resource 1", resourceOptional.get().getTitle());
        assertEquals("LINK", resourceOptional.get().getType());

        // Verificar interacciones con el repositorio
        verify(resourceRepository, times(1)).findById(1L);
    }

    /**
     * Test para obtener un recurso por ID (no existe).
     */
    @Test
    void testGetResourceById_NotFound() {
        // Configurar el comportamiento del repositorio
        when(resourceRepository.findById(999L)).thenReturn(Optional.empty());

        // Ejecutar el método
        Optional<Resource> resourceOptional = resourceService.getResourceById(999L);

        // Verificar el resultado
        assertFalse(resourceOptional.isPresent());

        // Verificar interacciones con el repositorio
        verify(resourceRepository, times(1)).findById(999L);
    }

    /**
     * Test para obtener recursos filtrados por categoría y subcategoría.
     */
    @Test
    void testGetResources_Success() {
        // Preparar datos de prueba
        List<Resource> mockResources = Arrays.asList(
                new Resource("Resource 1", "Desc 1", "FRONTEND", "LIBRERÍAS", "LINK", "https://example.com/resource1", null, null, null, null),
                new Resource("Resource 2", "Desc 2", "BACKEND", "FRAMEWORKS", "FILE", null, new byte[]{1, 2, 3}, "application/pdf", "file.pdf", null)
        );

        // Configurar el comportamiento del repositorio
        when(resourceRepository.findByCategoryAndSubcategory("BACKEND", "FRAMEWORKS"))
                .thenReturn(mockResources.subList(1, 2)); // Devolvemos solo los recursos que coinciden

        // Ejecutar el método
        List<Resource> resources = resourceService.getResources("BACKEND", "FRAMEWORKS");

        // Verificar el resultado
        assertNotNull(resources);
        assertEquals(1, resources.size());
        assertEquals("Resource 2", resources.get(0).getTitle());
        assertEquals("FILE", resources.get(0).getType());

        // Verificar interacciones con el repositorio
        verify(resourceRepository, times(1)).findByCategoryAndSubcategory("BACKEND", "FRAMEWORKS");
    }

    /**
     * Test para guardar un recurso.
     */
    @Test
    void testSaveResource() throws IOException {
        // Preparar datos de prueba
        String title = "Spring Boot Guide";
        String description = "PDF guide for Spring Boot";
        String category = "BACKEND";
        String subcategory = "FRAMEWORKS";
        String url = null; // URL debe ser nula para recursos de tipo FILE
        MultipartFile file = new MockMultipartFile("file", "guide.pdf", "application/pdf", "file content".getBytes());

        // Configurar el comportamiento del repositorio
        when(resourceRepository.save(any(Resource.class))).thenAnswer(invocation -> {
            Resource savedResource = invocation.getArgument(0);
            savedResource.setId(1L); // Simulamos la generación de ID
            return savedResource;
        });

        // Ejecutar el método
        Resource savedResource = resourceService.saveResource(title, description, category, subcategory, url, file);

        // Verificar el resultado
        assertNotNull(savedResource);
        assertEquals("Spring Boot Guide", savedResource.getTitle());
        assertEquals("BACKEND", savedResource.getCategory());
        assertEquals("FRAMEWORKS", savedResource.getSubcategory());
        assertNull(savedResource.getUrl()); // URL debe ser nula para recursos de tipo FILE
        assertEquals("guide.pdf", savedResource.getFileName());
        assertEquals("application/pdf", savedResource.getFileType());
        assertArrayEquals("file content".getBytes(), savedResource.getFileData());

        // Verificar interacciones con el repositorio
        verify(resourceRepository, times(1)).save(any(Resource.class));
    }
}