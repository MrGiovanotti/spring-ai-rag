package com.mrgiovanotti.practices.springairag.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class RagServiceImpl implements RagService {

  private final VectorStore vectorStore;
  private final ChatClient chatClient;

  public RagServiceImpl(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
    this.vectorStore = vectorStore;
    this.chatClient = chatClientBuilder.build();
  }

  private static final String SEPARATOR = "\n\n---\n\n";

  @Override
  public String askAi(String question) {
    SearchRequest searchRequest = SearchRequest
        .builder()
        .query(question)
        .topK(4)
        .build();
    List<Document> results = vectorStore.similaritySearch(searchRequest);
    String context = results.stream().map(Document::getText).collect(Collectors.joining(SEPARATOR));
    String prompt = """
        Responde solo con la información del conteto.
        Si no está en el contexto di: "No encontré información en los documentos."
        CONTEXTO:
        %s
        
        PREGUNTA:
        %s
        """.formatted(context, question);
    return chatClient.prompt().user(prompt).call().content();
  }
}
