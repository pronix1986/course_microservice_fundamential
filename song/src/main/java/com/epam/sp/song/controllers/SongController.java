package com.epam.sp.song.controllers;

import com.epam.sp.song.models.SongId;
import com.epam.sp.song.models.SongIds;
import com.epam.sp.song.models.SongMetadata;
import com.epam.sp.song.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<SongId> handleMetadata(@RequestBody Map<String, String> metadataMap) {
        final var resourceId = songService.save(metadataMap);
        // In the requirements we should return "OK", not "Created"
        return ResponseEntity.ok(resourceId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getMetadata(@PathVariable Integer id) {
        SongMetadata songMetadata = songService.findById(id);
        return new ResponseEntity<>(songMetadata.getMetadata(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SongIds> deleteMetadata(@PathVariable String ids) {
        final var songIds = songService.deleteByIds(ids);
        return ResponseEntity.ok(songIds);
    }
}