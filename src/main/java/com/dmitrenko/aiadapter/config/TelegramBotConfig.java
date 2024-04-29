package com.dmitrenko.aiadapter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class TelegramBotConfig {

    @Value("${integration.http.telegram.bot.url}")
    private String url;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(url).build();
    }
}
