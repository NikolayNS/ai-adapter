package com.dmitrenko.aiadapter.integration.http;

import com.dmitrenko.aiadapter.service.OpenAIService;
import com.dmitrenko.aiadapter.util.CustomMultipartFile;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.InputStream;
import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Getter
@Service
public class TelegramBotHandler extends SpringWebhookBot {
    private final String botToken;
    private final String botUsername;
    private final String botPath;

    private final OpenAIService openAIService;

    public TelegramBotHandler(SetWebhook setWebhook,
                              @Value("${integration.http.telegram.bot.token}")String botToken,
                              @Value("${integration.http.telegram.bot.username}") String botUsername,
                              @Value("${integration.http.telegram.bot.path}") String botPath,
                              OpenAIService openAIService) {
        super(setWebhook, botToken);
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.botPath = botPath;
        this.openAIService = openAIService;
    }

    @SneakyThrows
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        log.info("Received update: {}", update);
        var message = update.getMessage();
        if (update.hasMessage()) {
            if (message.hasText()) {
                var aiResponse = openAIService.chat(message.getText());
                execute(new SendMessage(String.valueOf(message.getChatId()), aiResponse));
            }
            if (message.hasVoice()) {
                GetFile getFile = new GetFile(message.getVoice().getFileId());
                File file = execute(getFile);
                CustomMultipartFile multipartFile;
                try (InputStream stream = downloadFileAsInputStream(file.getFilePath())) {
                    multipartFile = new CustomMultipartFile(stream.readAllBytes(), message.getVoice().getFileId(), ".ogg");
                }
                execute(new SendVoice(String.valueOf(message.getChatId()), new InputFile(openAIService.conversation(multipartFile).getInputStream(), UUID.randomUUID().toString())));
            }
        }

        log.info("update: {}", update);
        return null;
    }

    @Override
    @SneakyThrows
    public <T extends Serializable, M extends BotApiMethod<T>> T execute(M method) {
        return super.execute(method);
    }

    @SneakyThrows
    public InputStream downloadFileAsInputStream(String filePath) {
        return downloadFileAsStream(filePath);
    }
}
