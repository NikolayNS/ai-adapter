package com.dmitrenko.aiadapter.integration.http.controller;

import com.dmitrenko.aiadapter.integration.http.TelegramBotHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
@Log4j2
public class WebhookController {

    private final TelegramBotHandler telegramBotHandler;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/callback/update")
    public void onUpdateReceived(@RequestBody Update update) {
		try {
			telegramBotHandler.onWebhookUpdateReceived(update);
		} catch (Exception e) {
			log.error("Error while processing update", e);
		}
    }
}
