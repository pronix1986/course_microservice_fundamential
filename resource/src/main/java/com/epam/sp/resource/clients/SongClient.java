package com.epam.sp.resource.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "songClient", url = "${song.api.url}")
public interface SongClient {
    @PostMapping("/songs")
    void sendMetadata(@RequestBody Map<String, String> metadata);
}