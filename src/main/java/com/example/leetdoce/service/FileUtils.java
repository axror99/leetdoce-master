package com.example.leetdoce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileUtils {
    private static final String FILE_PATH = "src/foto/";

    public static byte[] getImageBytes(String image) {
        Path path = Paths.get(FILE_PATH, image);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }
}