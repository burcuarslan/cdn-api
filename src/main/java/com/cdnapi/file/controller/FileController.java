package com.cdnapi.file.controller;

import com.cdnapi.file.dto.FileUploadResponse;
import com.cdnapi.file.service.FileService;
import com.cdnapi.response.DefaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PreAuthorize("hasAnyAuthority('ADMIN','WRITER')")
    @PostMapping
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "fileName", required = false) String fileName) {

        String url = fileService.uploadFile(file, fileName);
        FileUploadResponse response = new FileUploadResponse(
                fileName != null ? fileName : file.getOriginalFilename(),
                url,
                file.getSize(),
                file.getContentType()
        );

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WRITER')")
    @DeleteMapping
    public ResponseEntity<DefaultResponse> deleteFile(@RequestParam("fileName") String fileName) {
        fileService.deleteFile(fileName);
        return ResponseEntity.ok(new DefaultResponse("Dosya silindi.", 200, System.currentTimeMillis()));
    }
}