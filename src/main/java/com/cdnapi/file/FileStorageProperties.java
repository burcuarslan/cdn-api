package com.cdnapi.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties(prefix = "file")
@Data
public class FileStorageProperties {
    @NotBlank(message = "Upload dizini boş olamaz")
    private String uploadDir;

    @NotNull(message = "İzin verilen uzantılar boş olamaz")
    private String[] allowedExtensions = {"jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "txt"};
}
