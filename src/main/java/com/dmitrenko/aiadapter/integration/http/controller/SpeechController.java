package com.dmitrenko.aiadapter.integration.http.controller;

import com.dmitrenko.aiadapter.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.dmitrenko.aiadapter.util.AIAdapterConstant.CHAT;
import static com.dmitrenko.aiadapter.util.AIAdapterConstant.CONVERSATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class SpeechController {

    private final OpenAIService openAIService;

    @PostMapping(value = CHAT, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public String conversation(@RequestBody String message) {
        return openAIService.chat(message);
    }

    @PostMapping(value = CONVERSATION, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> conversation(@RequestParam("clientSpeech") MultipartFile clientSpeech) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.mp3\"")
                .body(openAIService.conversation(clientSpeech));
    }
}
