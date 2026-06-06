package com.mrgiovanotti.practices.springairag.services;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentIndexService {

  void indexDocument(MultipartFile multipartFile) throws IOException;

}
