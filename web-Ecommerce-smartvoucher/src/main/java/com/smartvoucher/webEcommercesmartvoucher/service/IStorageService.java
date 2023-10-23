package com.smartvoucher.webEcommercesmartvoucher.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IStorageService {
    String storeFile (MultipartFile file);
    Stream<Path> loadAll();
    List<String> getAllUrl();
    byte[] readFileContent(String fileName);
    void deleteAllFiles();
}
