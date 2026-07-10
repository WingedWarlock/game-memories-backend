package com.fabio.gamememories.service;

import com.fabio.gamememories.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${app.storage.path:storage}")
    private String storagePath;

    public String save(MultipartFile file, String subfolder) {
        try {
            Path dir = Paths.get(storagePath, subfolder);
            Files.createDirectories(dir);

            String extension = getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID() + extension;
            Path destination = dir.resolve(filename);

            file.transferTo(destination);

            return subfolder + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public void delete(String filePath) {
        try {
            Path path = Paths.get(storagePath, filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar arquivo: " + e.getMessage());
        }
    }

    public Path resolve(String filePath) {
        Path path = Paths.get(storagePath).resolve(filePath).normalize();
        if (!Files.exists(path)) {
            throw new NotFoundException("Arquivo não encontrado: " + filePath);
        }
        return path;
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }
}
