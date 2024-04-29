package com.dmitrenko.aiadapter.mapper;

import com.dmitrenko.aiadapter.model.dto.openai.MessageRoleEnum;
import com.dmitrenko.aiadapter.model.dto.openai.VoiceEnum;
import com.dmitrenko.aiadapter.model.dto.openai.request.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OpenAIMapper {

    public ChatRequest toChatReq(String model, String message) {
        var list = new LinkedList<ChatRequestTool>();

        var switchLamp = new ChatRequestTool();
        switchLamp
                .setType("function")
                .setFunction(new ChatRequestToolFunction()
                        .setName("switchLamp")
                        .setDescription("Switching lamps throughout the house, by lamp number.")
                        .setParameters(new FunctionParameters()
                                .setType("object")
                                .setProperties(new ParametersProperties()
                                        .setAction(new PropertiesAction<LampActionEnum>()
                                                .setType("string")
                                                .setAction(LampActionEnum.values()))
                                        .setNumber(new PropertiesNumber().setType("number")))));
        list.add(switchLamp);

        var switchSocket = new ChatRequestTool();
        switchSocket
                .setType("function")
                .setFunction(new ChatRequestToolFunction()
                        .setName("switchSocket")
                        .setDescription("Switching socket throughout the house, by socket number.")
                        .setParameters(new FunctionParameters()
                                .setType("object")
                                .setProperties(new ParametersProperties()
                                        .setAction(new PropertiesAction<SocketActionEnum>()
                                                .setType("string")
                                                .setAction(SocketActionEnum.values()))
                                        .setNumber(new PropertiesNumber().setType("number")))));
        list.add(switchSocket);

        var setACTemperature = new ChatRequestTool();
        setACTemperature
                .setType("function")
                .setFunction(new ChatRequestToolFunction()
                        .setName("setACTemperature")
                        .setDescription("Set AC temperature by action and number")
                        .setParameters(new FunctionParameters()
                                .setType("object")
                                .setProperties(new ParametersProperties()
                                        .setAction(new PropertiesAction<SocketActionEnum>()
                                                .setType("string")
                                                .setAction(SocketActionEnum.values()))
                                        .setNumber(new PropertiesNumber()
                                                .setType("number")))));
        list.add(setACTemperature);

        return new ChatRequest()
                .setModel(model)
                .setMessages(List.of(
                        new ChatRequestMessage()
                                .setRole(MessageRoleEnum.SYSTEM.getValue())
                                .setContent("You are a smart home manager. " +
                                        "Your responsibilities include communicating with the resident of the house and managing their devices, such as sockets, lamps, air conditioning, and so on. " +
                                        "Speak the language in which you are spoken to. " +
                                        "After turning on any device from your parameters, answer the user that you turned it on."),
                        new ChatRequestMessage()
                                .setRole(MessageRoleEnum.USER.getValue())
                                .setContent(message)))
                .setTools(list);
    }

    public TextToSpeechRequest toSpeechReq(String model, String message) {
        return new TextToSpeechRequest()
                .setModel(model)
                .setInput(message)
                .setVoice(VoiceEnum.NOVA.getValue());
    }
}
