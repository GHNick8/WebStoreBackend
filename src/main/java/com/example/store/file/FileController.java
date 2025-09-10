package com.example.store.file;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists() && !dir.mkdirs()) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Could not create upload directory"));
        }

        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("file.bin");
        String clean = original.replaceAll("[^a-zA-Z0-9._-]", "_");
        String unique = UUID.randomUUID() + "-" + clean;

        File dest = new File(dir, unique);
        file.transferTo(dest);

        String publicUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()   
                .path("/uploads/")
                .path(unique)
                .toUriString();

        return ResponseEntity.ok(Map.of("url", publicUrl));
    }
}
