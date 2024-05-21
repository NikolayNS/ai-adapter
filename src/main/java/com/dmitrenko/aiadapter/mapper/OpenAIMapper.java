package com.dmitrenko.aiadapter.mapper;

import com.dmitrenko.aiadapter.model.dto.openai.request.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static com.dmitrenko.aiadapter.model.FunctionEnum.*;
import static com.dmitrenko.aiadapter.model.dto.openai.MessageRoleEnum.SYSTEM;
import static com.dmitrenko.aiadapter.model.dto.openai.MessageRoleEnum.USER;
import static com.dmitrenko.aiadapter.model.dto.openai.VoiceEnum.NOVA;

@Service
public class OpenAIMapper {

    public ChatRequest toChatReq(String model, String message) {
        var list = new LinkedList<ChatRequestTool>();

	    var testAction = new ChatRequestTool();
	    testAction
			    .setType("function")
			    .setFunction(new ChatRequestToolFunction()
					    .setName(TEST_ACTION.getValue())
					    .setDescription("Perform a test action"));
	    list.add(testAction);

        var switchLamp = new ChatRequestTool();
        switchLamp
                .setType("function")
                .setFunction(new ChatRequestToolFunction()
                        .setName(SWITCH_LAMP.getValue())
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
                        .setName(SWITCH_SOCKET.getValue())
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
                        .setName(SET_AC_TEMPERATURE.getValue())
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
                                .setRole(SYSTEM.getValue())
                                .setContent("You are a smart home manager. " +
                                        "Your responsibilities include communicating with the resident of the house and managing their devices, such as sockets, lamps, air conditioning, and so on. " +
                                        "Speak the language in which you are spoken to. " +
                                        "After turning on any device from your parameters, answer the user that you turned it on."),
                        new ChatRequestMessage()
                                .setRole(USER.getValue())
                                .setContent(message)))
                .setTools(list);
    }

    public TextToSpeechRequest toSpeechReq(String model, String message) {
        return new TextToSpeechRequest()
                .setModel(model)
                .setInput(message)
                .setVoice(NOVA.getValue());
    }
}
