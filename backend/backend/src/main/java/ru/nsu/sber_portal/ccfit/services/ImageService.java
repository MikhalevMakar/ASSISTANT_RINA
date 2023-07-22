package ru.nsu.sber_portal.ccfit.services;

import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.exceptions.FindRestByTitleException;
import ru.nsu.sber_portal.ccfit.repositories.ImageRepository;

import java.io.ByteArrayInputStream;

@Service
@Data
public class ImageService {

    private final ImageRepository imageRepository;

    public ResponseEntity<InputStreamResource> findImageById(Long id) {
        return imageRepository.findById(id)
            .map(image -> ResponseEntity.ok()
                .header(image.getName(), image.getName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes()))))
            .orElseThrow(() -> new FindRestByTitleException("The photo wasn't found by id"));
    }
}
