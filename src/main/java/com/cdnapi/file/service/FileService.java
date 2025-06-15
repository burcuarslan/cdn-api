package com.cdnapi.file.service;

import com.cdnapi.file.FileStorageProperties;
import com.cdnapi.file.exception.FileDeleteException;
import com.cdnapi.file.exception.FileUploadException;
import com.cdnapi.file.scan.VirusScanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileStorageProperties fileStorageProperties;
    private final VirusScanService virusScanService;

    public String uploadFile(MultipartFile file, String fileName) {
        try {
            // Dosya virüs taraması
//            if (!virusScanService.isClean(file.getInputStream())) {
//                throw new FileUploadException("File contains virus");
//            }

            fileName = StringUtils.cleanPath(fileName == null ? file.getOriginalFilename() : fileName);
            validateFile(file, fileName);

            Path uploadPath = Paths.get(fileStorageProperties.getUploadDir());
            Path filePath = uploadPath.resolve(fileName);
            Files.createDirectories(uploadPath);

            if (Files.exists(filePath)) {
                throw new FileUploadException("File already exists: " + fileName);
            }

            Files.copy(file.getInputStream(), filePath);
            return fileStorageProperties.getUploadDir() + fileName;

        } catch (IOException e) {
            log.error("File upload failed: " + e.getMessage(), e);
            throw new FileUploadException("File upload failed: " + e.getMessage(), e);
        } catch (FileUploadException e) {
            log.error("File upload failed: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("File upload failed: " + e.getMessage(), e);
            throw new FileUploadException("File upload failed: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(fileStorageProperties.getUploadDir()).resolve(fileName);
            if (!Files.deleteIfExists(filePath)) {
                throw new FileDeleteException("File not found: " + fileName);
            }
        } catch (IOException e) {
            throw new FileDeleteException("Failed to delete file: " + e.getMessage(), e);
        }
    }

    private void validateFile(MultipartFile file, String fileName) {
        if (file.isEmpty()) {
            throw new FileUploadException("File does not exist");
        }

        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        if (!isValidFileExtension(fileExtension)) {
            throw new FileUploadException("Invalid file type. Allowed extensions: " + String.join(", ", fileStorageProperties.getAllowedExtensions()));
        }
    }

    private boolean isValidFileExtension(String extension) {
        return java.util.Arrays.stream(fileStorageProperties.getAllowedExtensions())
                .anyMatch(allowedExt -> allowedExt.equalsIgnoreCase(extension));
    }
}
