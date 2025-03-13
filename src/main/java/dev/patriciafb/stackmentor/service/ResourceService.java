package dev.patriciafb.stackmentor.service;

import java.util.List;

import org.springframework.stereotype.Service;
import dev.patriciafb.stackmentor.model.Resource;
import dev.patriciafb.stackmentor.repository.ResourceRepository;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


import java.util.Optional;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource saveResource(String title, String description, String category, String subcategory, String url, MultipartFile file) throws IOException {
        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setDescription(description);
        resource.setCategory(category);
        resource.setSubcategory(subcategory);

        // Generar URL de acceso al archivo
        if (url == null || url.isEmpty() && file != null) {
            url = "http://localhost:8080/api/resources/file/" + (resource.getId() != null ? resource.getId() : "new-file");
        }
        resource.setUrl(url);

        // Guardar el archivo en la base de datos si se proporciona
        if (file != null && !file.isEmpty()) {
            resource.setFileName(file.getOriginalFilename());
            resource.setFileType(file.getContentType());
            resource.setFileData(file.getBytes());
        }

        return resourceRepository.save(resource);
    }

    public List<Resource> getResources(String category, String subcategory) {
        return resourceRepository.findByCategoryAndSubcategory(category, subcategory);
    }

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Optional<Resource> getResourceByFileName(String fileName) {
        return resourceRepository.findByFileName(fileName);
    }
}
