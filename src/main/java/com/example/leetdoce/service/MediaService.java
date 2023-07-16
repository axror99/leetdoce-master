package com.example.leetdoce.service;

import com.example.leetdoce.entity.Media;
import com.example.leetdoce.exception.GetBytesException;
import com.example.leetdoce.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    public String savePicture(MultipartFile picture) {

        String originalFileName = picture.getOriginalFilename();
        long size = picture.getSize();
        String contentType = picture.getContentType();
        String[] split = originalFileName.split("\\.");
        String randomName = UUID.randomUUID().toString()+"."+split[split.length-1];
        byte[] bytes;
        try {
            bytes = picture.getBytes();
        } catch (IOException e) {
            throw new GetBytesException(e);
        }
        Media media = new Media();
        media.setName(randomName);
        media.setSize(size);
        media.setBytes(bytes);
        media.setFileOriginalName(originalFileName);
        media.setContentType(contentType);
        mediaRepository.save(media);
        return randomName;
    }
}
