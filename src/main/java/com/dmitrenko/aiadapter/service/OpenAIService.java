package com.dmitrenko.aiadapter.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface OpenAIService {

    Resource conversation(MultipartFile clientSpeech);

    String chat(String message);
}
