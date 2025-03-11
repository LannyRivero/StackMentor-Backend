package dev.patriciafb.stackmentor.services;

import dev.patriciafb.stackmentor.models.Resource;
import dev.patriciafb.stackmentor.repositories.ResourceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResourceService {
    
    private ResourceRepository resourceRepository;
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
    public Resource saveResource(Resource resource) {
        return resourceRepository.save(resource);
    }
    public List<Resource> getResourcesByCategory(String category) {
        return resourceRepository.findByCategory(category);
    }
}
