package com.epam.sp.resource;


import com.epam.sp.resource.configuration.WireMockConfig;
import com.epam.sp.resource.models.ResourceId;
import com.epam.sp.resource.repositories.ResourceRepository;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@WireMockTest(httpPort = 8081)
class MainIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ResourceRepository resourceRepository;

    @BeforeEach
    void setUp() {
        WireMockConfig.configureStubs();
    }

    @Test
    void resource_ShouldCreateResource_WhenValidMp3FileIsProvided() throws IOException {
        final var baseUrl = "http://localhost:" + port + "/api/v1/resources";

        final var mp3FileName = "file_example_MP3_700KB.mp3";

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        final var parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("file", new ClassPathResource(mp3FileName));

        final var request = new HttpEntity<>(parameters, headers);

        final var response = template.postForEntity(baseUrl, request, ResourceId.class);
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final var listResources = resourceRepository.findAll();
        assertEquals(1, listResources.size());
        assertDoesNotThrow(() -> resourceRepository.findById(1).orElseThrow());

    }

}
