package com.epam.sp.controllers;

import com.epam.sp.models.ResourceId;
import com.epam.sp.models.ResourceIds;
import com.epam.sp.services.ResourceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping(consumes = "audio/mpeg", produces = "application/json")
    public ResponseEntity<ResourceId> addResource(@RequestBody MultipartFile file) {
        final var resourceId = resourceService.store(file);
        // In the requirements we should return "OK", not "Created"
        return ResponseEntity.ok(resourceId);
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> getResource(@PathVariable Integer id) {
        final var resource = resourceService.findById(id);
        final var file = resource.getFile();
        final var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("audio/mpeg"));
        httpHeaders.setContentLength(file.length);
        return new ResponseEntity<>(file, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<ResourceIds> deleteResource(@PathVariable String ids) {
        final var resourceIds = resourceService.deleteByIds(ids);
        return ResponseEntity.ok(resourceIds);
    }




}
