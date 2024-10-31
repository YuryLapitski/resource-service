package com.epam.learn.resource_service.service;

import com.epam.learn.resource_service.dao.ResourceRepository;
import com.epam.learn.resource_service.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public Resource createResource(MultipartFile file) throws IOException {
        Resource resource = new Resource();
        resource.setData(file.getBytes());
        return resourceRepository.save(resource);
    }

    public byte[] getResourceDataById(int id) {
        return resourceRepository.findById(id).map(Resource::getData).orElse(null);
    }

    public List<Integer> deleteResources(String idsCSV) {
        List<Integer> ids = Arrays.stream(idsCSV.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        resourceRepository.deleteAllById(ids);
        return ids;
    }
}