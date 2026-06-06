package com.mrgiovanotti.practices.springairag.controllers;

import com.mrgiovanotti.practices.springairag.services.DocumentIndexService;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentIndexController {

  private final DocumentIndexService documentIndexService;
  private static final String MESSAGE_KEY = "message";

  @PostMapping("/upload")
  public Map<String, String> uploadAndIndexPdf(@RequestParam("file") MultipartFile multipartFile) {
    if (multipartFile == null || multipartFile.isEmpty()) {
      return Map.of(MESSAGE_KEY, "File is empty");
    }
    if (multipartFile.getContentType() == null
        || !Objects.equals(multipartFile.getContentType(), "application/pdf")) {
      return Map.of(MESSAGE_KEY, "File is not a PDF file");
    }
    try {
      documentIndexService.indexDocument(multipartFile);
      return Map.of(MESSAGE_KEY, "Document has been indexed");
    } catch (IOException exception) {
      return Map.of(MESSAGE_KEY, "Error indexing document: " + exception.getMessage());
    }
  }

}
