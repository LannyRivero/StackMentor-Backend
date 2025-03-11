package dev.patriciafb.stackmentor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.patriciafb.stackmentor.models.Resource;
import dev.patriciafb.stackmentor.services.ResourceService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    
    private ResourceService resourceService;

    // Obtener todos los recursos
    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    // Obtener recursos por categoría
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Resource>> getResourcesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(resourceService.getResourcesByCategory(category));
    }

    // Crear un recurso
    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {
        return ResponseEntity.status(201).body(resourceService.saveResource(resource));
    }
}
