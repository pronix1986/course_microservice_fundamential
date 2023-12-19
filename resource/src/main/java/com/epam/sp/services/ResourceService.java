package com.epam.sp.services;

import com.epam.sp.clients.SongClient;
import com.epam.sp.exceptions.FileUploadException;
import com.epam.sp.exceptions.ResourceNotFoundException;
import com.epam.sp.models.Resource;
import com.epam.sp.models.ResourceId;
import com.epam.sp.models.ResourceIds;
import com.epam.sp.repositories.ResourceRepository;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ValidatorService validatorService;
    private final SongClient songClient;

    public ResourceService(ResourceRepository resourceRepository, ValidatorService validatorService, SongClient songClient) {
        this.resourceRepository = resourceRepository;
        this.validatorService = validatorService;
        this.songClient = songClient;
    }

    public ResourceId store(MultipartFile file) {
        validatorService.validateMediaInput(file);
        final var resource = new Resource();
        try {
            final var fileAsBytes = file.getBytes();
            resource.setFile(fileAsBytes);
            final var id = store(resource).getId();
            final var metadata = extractMetadata(fileAsBytes);
            metadata.put("resourceId", String.valueOf(id));
            songClient.sendMetadata(metadata);
            return new ResourceId(id);
        } catch (IOException | TikaException | SAXException e) {
            throw new FileUploadException("Cannot upload:", e);
        }
    }

    private Map<String, String> extractMetadata(byte[] fileAsBytes) throws TikaException, IOException, SAXException {
        final var metadataMap = new HashMap<String, String>();
        final var parser = new Mp3Parser();
        final var handler = new BodyContentHandler();
        final var metadata = new Metadata();
        final var context = new ParseContext();
        final var stream = new ByteArrayInputStream(fileAsBytes);
        parser.parse(stream, handler, metadata, context);
        for (var name : metadata.names()) {
            metadataMap.put(name, metadata.get(name));
        }
        return metadataMap;

    }

    public Resource store(Resource resource) {
        return resourceRepository.save(resource);
    }

    public Resource findById(Integer id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource does not exist: " + id));
    }


    public ResourceIds deleteByIds(String ids) {
        validatorService.validateCsvInput(ids);
        final var idList = Arrays.stream(ids.split("[,;]")).map(Integer::parseInt).toList();
        // 'deleteAll' methods swallow unknown ids without exception which conforms to the requirements
        resourceRepository.deleteAllById(idList);
        return new ResourceIds(idList);
    }
}
