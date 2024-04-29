package com.dmitrenko.aiadapter.integration.http.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@AllArgsConstructor
public abstract class BaseClient {

    private WebClient webClient;

    public <R> R get(String url, Class<R> response) {
        return webClient
                .get()
                .uri(fromHttpUrl(url).build().toUri())
                .retrieve()
                .bodyToMono(response)
                .block();
    }

    public <R> R post(String url, Class<R> response) {
        return webClient
                .post()
                .uri(fromHttpUrl(url).build().toUri())
                .retrieve()
                .bodyToMono(response)
                .block();
    }

    public <R, B> R post(String url, B body, Class<R> response) {
        return webClient
                .post()
                .uri(fromHttpUrl(url).build().toUri())
                .contentType(APPLICATION_JSON)
                .body(Mono.just(body), body.getClass())
                .retrieve()
                .bodyToMono(response)
                .block();
    }

    public <R, B> R post(String url, HttpHeaders headers, B body, Class<R> response) {
        return webClient
                .post()
                .uri(fromHttpUrl(url).build().toUri())
                .contentType(APPLICATION_JSON)
                .headers(o -> o.addAll(headers))
                .body(Mono.just(body), body.getClass())
                .retrieve()
                .bodyToMono(response)
                .block();
    }

    public <R> R post(String url, MultiValueMap<String, ?> multipartData, Class<R> response) {
        return webClient
                .post()
                .uri(url)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartData))
                .retrieve()
                .bodyToMono(response)
                .block();
    }

    public <R, B> R put(String url, B body, Class<R> response) {
        return webClient
                .put()
                .uri(fromHttpUrl(url).build().toUri())
                .contentType(APPLICATION_JSON)
                .body(Mono.just(body), body.getClass())
                .retrieve()
                .bodyToMono(response)
                .block();
    }

    public <R> R delete(String url, Class<R> response) {
        return webClient
                .delete()
                .uri(fromHttpUrl(url).build().toUri())
                .retrieve()
                .bodyToMono(response)
                .block();
    }
}
