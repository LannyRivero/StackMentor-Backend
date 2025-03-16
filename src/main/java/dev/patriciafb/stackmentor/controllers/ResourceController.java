package dev.patriciafb.stackmentor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import dev.patriciafb.stackmentor.models.Resource;
import dev.patriciafb.stackmentor.services.ResourceService;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public ResponseEntity<Resource> uploadResource(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("subcategory") String subcategory,
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            Resource savedResource = resourceService.saveResource(title, description, category, subcategory, url, file);
            return ResponseEntity.ok(savedResource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getResources(
            @RequestParam String category,
            @RequestParam String subcategory) {
        return ResponseEntity.ok(resourceService.getResources(category, subcategory));
    }

    @GetMapping("/file/name/{fileName}")
    public ResponseEntity<byte[]> viewFile(@PathVariable String fileName) {
        Optional<Resource> resourceOptional = resourceService.getResourceByFileName(fileName);

        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            byte[] fileData = resource.getFileData();

            if (fileData == null || fileData.length == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(resource.getFileType()));
            headers.setContentDisposition(ContentDisposition.inline().filename(resource.getFileName()).build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/file/json/id/{id}")
    public ResponseEntity<Map<String, String>> getFileAsJson(@PathVariable Long id) {
        Optional<Resource> resourceOptional = resourceService.getResourceById(id);

        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            byte[] fileData = resource.getFileData();

            if (fileData == null || fileData.length == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            String base64File = Base64.getEncoder().encodeToString(fileData);

            Map<String, String> response = new HashMap<>();
            response.put("fileName", resource.getFileName());
            response.put("fileType",
                    resource.getFileType() != null ? resource.getFileType() : "application/octet-stream");
            response.put("fileData", base64File);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/file/json/name/{fileName}")
    public ResponseEntity<Map<String, String>> viewFileAsJson(@PathVariable String fileName) {
        Optional<Resource> resourceOptional = resourceService.getResourceByFileName(fileName);

        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            byte[] fileData = resource.getFileData();

            if (fileData == null || fileData.length == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            String base64File = Base64.getEncoder().encodeToString(fileData);

            Map<String, String> response = new HashMap<>();
            response.put("fileName", resource.getFileName());
            response.put("fileType",
                    resource.getFileType() != null ? resource.getFileType() : "application/octet-stream");
            response.put("fileData", base64File);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}