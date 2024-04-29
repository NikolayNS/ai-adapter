package com.dmitrenko.aiadapter.integration.http.client;

import com.dmitrenko.aiadapter.exception.FileNotReadable;
import com.dmitrenko.aiadapter.model.dto.openai.request.ChatRequest;
import com.dmitrenko.aiadapter.model.dto.openai.request.TextToSpeechRequest;
import com.dmitrenko.aiadapter.model.dto.openai.response.ChatResponse;
import com.dmitrenko.aiadapter.util.MultipartInputStreamFileResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class OpenAIClient extends BaseClient {

    private final String baseUrl;

    private final String assistant;
    private final String chatUrl;
    private final String speechToText;
    private final String textToSpeech;

    public OpenAIClient(@Qualifier("openAIWebClient") WebClient webClient,
                        @Value("${integration.http.open-ai.base-url}") String baseUrl,
                        @Value("${integration.http.open-ai.assistant}") String assistant,
                        @Value("${integration.http.open-ai.chat-url}") String chatUrl,
                        @Value("${integration.http.open-ai.speech-to-text}") String speechToText,
                        @Value("${integration.http.open-ai.text-to-speech}") String textToSpeech) {
        super(webClient);
        this.baseUrl = baseUrl;
        this.assistant = assistant;
        this.chatUrl = chatUrl;
        this.speechToText = speechToText;
        this.textToSpeech = textToSpeech;
    }

    public String speechToText(String model, MultipartFile file) {
        MultiValueMap<String, Object> multipartData = new LinkedMultiValueMap<>();
        multipartData.add("model", model);
        multipartData.add("file", new MultipartInputStreamFileResource(getInputStream(file), file.getOriginalFilename()));

        return post(baseUrl + speechToText, multipartData, String.class);
    }

    public ChatResponse textToText(ChatRequest request) {
        return post(baseUrl + chatUrl, request, ChatResponse.class);
    }

    public byte[] textToSpeech(TextToSpeechRequest request) {
        return post(baseUrl + textToSpeech, request, byte[].class);
    }


    private InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            log.error("File not readable " + e.getMessage());
            throw new FileNotReadable(e.getMessage());
        }
    }
}
