package com.epam.sp.services;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ValidatorService {

    @Value("song.allowed-csv-length")
    private Integer maxLength;

    public void validateMetadata(final Map<String, String> metadata) {
        if (MapUtils.isEmpty(metadata)) {
            throw new IllegalArgumentException("Metadata is empty");
        }
        if (StringUtils.isEmpty(metadata.get("resourceId"))) {
            throw new IllegalArgumentException("Metadata does not have a reference to a resource file");
        }
    }

    public void validateCsvInput(String input) {
        if (StringUtils.isEmpty(input)) {
            throw new IllegalArgumentException("Input is empty");
        }
        int length;
        if ((length = input.length()) > maxLength) {
            throw new IllegalArgumentException("Input is too long. Input length: " + length + ", while the allowed value is " + maxLength);
        }
    }


}
