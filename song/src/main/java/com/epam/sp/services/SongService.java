package com.epam.sp.services;

import com.epam.sp.exceptions.ResourceNotFoundException;
import com.epam.sp.models.SongId;
import com.epam.sp.models.SongIds;
import com.epam.sp.models.SongMetadata;
import com.epam.sp.repositories.MetadataRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class SongService {
    private final MetadataRepository metadataRepository;

    private final ValidatorService validatorService;

    public SongService(MetadataRepository metadataRepository, ValidatorService validatorService) {
        this.metadataRepository = metadataRepository;
        this.validatorService = validatorService;
    }

    public SongId save(Map<String, String> metadataMap) {
        validatorService.validateMetadata(metadataMap);
        final var songMetadata = new SongMetadata();
        songMetadata.setMetadata(metadataMap);
        final var resourceId = save(songMetadata).getId();
        return new SongId(resourceId);
    }

    public SongMetadata save(SongMetadata songMetadata) {
        return metadataRepository.save(songMetadata);
    }

    public SongMetadata findById(Integer id) {
        return metadataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There's no resource for id: " + id));
    }

    public SongIds deleteByIds(String ids) {
        validatorService.validateCsvInput(ids);
        final var idList = Arrays.stream(ids.split("[,;]")).map(Integer::parseInt).toList();
        // 'deleteAll' methods swallow unknown ids without exception which conforms to the requirements
        metadataRepository.deleteAllById(idList);
        return new SongIds(idList);

    }

    public void deleteById(Integer id) {
        metadataRepository.deleteById(id);
    }
}
