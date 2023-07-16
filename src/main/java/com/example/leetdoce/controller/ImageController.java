package com.example.leetdoce.controller;

import com.example.leetdoce.entity.Media;
import com.example.leetdoce.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

//    private final MediaService mediaService;
    private final MediaRepository mediaRepository;

    @GetMapping(
            value = "/{image}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<?> getImage(
            @PathVariable("image") String image
    ) {
        Optional<Media> media = mediaRepository.findByName(image);
//        byte[] imageBytes = FileUtils.getImageBytes(image);
        byte[] imageBytes = media.get().getBytes();
        assert imageBytes != null;
        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        return ResponseEntity.ok().contentLength(imageBytes.length).body(resource);
    }
}
