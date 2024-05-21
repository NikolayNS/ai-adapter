package com.dmitrenko.aiadapter.service.impl;

import com.dmitrenko.aiadapter.exception.EnumValueNotFoundException;
import com.dmitrenko.aiadapter.integration.http.client.OpenAIClient;
import com.dmitrenko.aiadapter.mapper.OpenAIMapper;
import com.dmitrenko.aiadapter.model.FunctionEnum;
import com.dmitrenko.aiadapter.service.OpenAIService;
import com.dmitrenko.aiadapter.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${integration.http.open-ai.models.text-to-speech}") private String textToSpeechModel;
    @Value("${integration.http.open-ai.models.assistant}") private String assistant;
    @Value("${integration.http.open-ai.models.speech-to-text}") private String speechToTextModel;

    private final TaskService taskService;

    private final OpenAIClient openAIClient;

    private final OpenAIMapper openAIMapper;

    @Override
    public String chat(String message) {
        var request = openAIMapper.toChatReq(assistant, message);

        var actionAnswer = openAIClient.textToText(request).getChoices().get(0).getMessage();

        actionAnswer.getToolCalls().forEach(o -> {
            var function = o.getFunction();
            switch (FunctionEnum.fromValue(function.getName())) {
	            case TEST_ACTION -> taskService.testAction();
	            case SWITCH_LAMP -> taskService.switchLamp(function.getArguments());
	            case SWITCH_SOCKET -> taskService.switchSocket(function.getArguments());
	            case SET_AC_TEMPERATURE -> taskService.setACTemperature(function.getArguments());
                default -> throw new EnumValueNotFoundException("Action not found");
            }
        });

        var actions = actionAnswer.getToolCalls().stream().map(o -> o.getFunction().getArguments()).collect(Collectors.joining(" ,"));
        var response = openAIMapper.toChatReq(assistant, String.format("You just performed these steps: %s. Comment on this in the format of doing this and that, according to the described arguments. For example, I turned on lamp number 1.", actions));

        return openAIClient.textToText(response)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }

    @Override
    public Resource conversation(MultipartFile clientSpeech) {
        var clientText = openAIClient.speechToText(textToSpeechModel, clientSpeech);

        var aiMessage = chat(clientText);

        var audioByte = openAIClient.textToSpeech(openAIMapper.toSpeechReq(speechToTextModel, aiMessage));

        return new ByteArrayResource(audioByte);
    }
}
