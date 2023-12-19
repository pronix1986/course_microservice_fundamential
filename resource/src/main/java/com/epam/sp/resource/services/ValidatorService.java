package com.epam.sp.resource.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ValidatorService {
    @Value("${resource.allowed-csv-length}")
    private Integer maxLength;

    private final Tika tika = new Tika();

    public void validateCsvInput(String input) {
        if (StringUtils.isEmpty(input)) {
            throw new IllegalArgumentException("Input is empty");
        }
        int length;
        if ((length = input.length()) > maxLength) {
            throw new IllegalArgumentException("Input is too long. Input length: " + length + ", while the allowed value is " + maxLength);
        }
    }

    public void validateMediaInput(MultipartFile file) {
        final var expectedContentType = "audio/mpeg";
        final var contentType = file.getContentType();
        try {
            final var detectedContentType = tika.detect(file.getInputStream());
            if (expectedContentType.equals(contentType)
                    && expectedContentType.equals(detectedContentType)) {
                return;
            }
            throw new IllegalArgumentException("Expected content type is 'audio/mpeg'");
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot determine content type", e);
        }

    }
}
