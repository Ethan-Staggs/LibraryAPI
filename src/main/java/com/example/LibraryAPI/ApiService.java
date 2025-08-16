package com.example.LibraryAPI;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {

    private final WebClient webClient;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://openlibrary.org/search").build();
    }

    public Mono<String> getBookByTitle(String title) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(".json")
                        .queryParam("q", title)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
