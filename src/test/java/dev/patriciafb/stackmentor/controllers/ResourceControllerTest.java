package dev.patriciafb.stackmentor.controllers;

import dev.patriciafb.stackmentor.models.Resource;
import dev.patriciafb.stackmentor.services.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadResourceWithUrl() throws IOException {

        String url = "https://example.com/resource";
        Resource mockResource = new Resource(
                "Test Resource",
                "Description",
                "FRONTEND",
                "LIBRERÍAS",
                "LINK",
                url,
                null,
                null,
                null,
                null);

        when(resourceService.saveResource(anyString(), anyString(), anyString(), anyString(), anyString(), isNull()))
                .thenReturn(mockResource);

        ResponseEntity<Resource> response = resourceController.uploadResource(
                "Test Resource",
                "Description",
                "FRONTEND",
                "LIBRERÍAS",
                url,
                null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(url, response.getBody().getUrl());
    }

    @Test
    void testGetResources() {

        List<Resource> mockResources = Arrays.asList(
                new Resource("Resource 1", "Desc 1", "BACKEND", "FRAMEWORKS", "LINK", "https://example.com/resource1",
                        null, null, null, null),
                new Resource("Resource 2", "Desc 2", "FRONTEND", "LIBRERÍAS", "FILE", null, new byte[] { 1, 2, 3 },
                        "application/pdf", "file.pdf", null));

        when(resourceService.getResources("BACKEND", "FRAMEWORKS")).thenReturn(mockResources);

        ResponseEntity<List<Resource>> response = resourceController.getResources("BACKEND", "FRAMEWORKS");

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testViewFileByFileName() {

        Resource mockResource = new Resource();
        mockResource.setFileType("application/pdf");
        mockResource.setFileName("test.pdf");
        mockResource.setFileData(new byte[] { 1, 2, 3 });

        when(resourceService.getResourceByFileName("test.pdf")).thenReturn(Optional.of(mockResource));

        ResponseEntity<byte[]> response = resourceController.viewFile("test.pdf");

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("application/pdf", response.getHeaders().getContentType().toString());
    }

    @Test
    void testViewFileAsJsonById() {
        Resource mockResource = new Resource();
        mockResource.setFileName("test.pdf");
        mockResource.setFileType("application/pdf");
        mockResource.setFileData(new byte[] { 1, 2, 3 });

        when(resourceService.getResourceById(1L)).thenReturn(Optional.of(mockResource));

        ResponseEntity<Map<String, String>> response = resourceController.getFileAsJson(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test.pdf", response.getBody().get("fileName"));
        assertEquals("application/pdf", response.getBody().get("fileType"));
    }
}