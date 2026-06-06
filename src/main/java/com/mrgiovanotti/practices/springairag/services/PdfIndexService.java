package com.mrgiovanotti.practices.springairag.services;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PdfIndexService implements DocumentIndexService{

  private final VectorStore vectorStore;

  @Override
  public void indexDocument(MultipartFile multipartFile) throws IOException {

    Resource resource = new InputStreamResource(multipartFile.getInputStream());

    PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(resource);
    List<Document> documents = pagePdfDocumentReader.get();

    TokenTextSplitter tokenTextSplitter = TokenTextSplitter
        .builder()
        .withChunkSize(800)
        .withMinChunkSizeChars(350)
        .withMinChunkLengthToEmbed(5)
        .withMaxNumChunks(10000)
        .withKeepSeparator(true)
        .build();

    List<Document> chunks = tokenTextSplitter.split(documents);
    chunks.forEach(chunk -> chunk.getMetadata().put("source",
        Objects.requireNonNull(multipartFile.getOriginalFilename())));
    vectorStore.add(chunks);

  }
}
