package com.mrgiovanotti.practices.springairag.controllers;

import com.mrgiovanotti.practices.springairag.services.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rag")
public class RagController {

  private final RagService ragService;

  @PostMapping("/ask")
  public String askAi(@RequestBody String question) {
    return ragService.askAi(question);
  }

}
