package com.epam.learn.resource_service.controller;

import com.epam.learn.resource_service.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> uploadResource(@RequestParam("file") MultipartFile file) {
        if (!"audio/mpeg".equalsIgnoreCase(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid file type: expected 'audio/mpeg' for MP3 files.");
        }

        try {
            var resource = resourceService.createResource(file);
            Map<String, Integer> result = new HashMap<>();
            result.put("id", resource.getId());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal server error has occurred.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResource(@PathVariable int id) {
        try {
            byte[] audioData = resourceService.getResourceDataById(id);
            if (audioData == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The resource with the specified ID does not exist.");
            }
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.parseMediaType("audio/mpeg"))
                    .body(audioData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal server error has occurred.");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteResources(@RequestParam String ids) {
        try {
            List<Integer> deletedIds = resourceService.deleteResources(ids);
            Map<String, List<Integer>> result = new HashMap<>();
            result.put("ids", deletedIds);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal server error has occurred.");
        }
    }
}
