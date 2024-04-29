package com.dmitrenko.aiadapter.config;

import com.dmitrenko.aiadapter.exception.ClientResponseException;
import com.dmitrenko.aiadapter.exception.ServerResponseException;
import com.dmitrenko.aiadapter.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import static com.dmitrenko.aiadapter.util.CommonConstant.AUTHORIZATION_HEADER;
import static com.dmitrenko.aiadapter.util.CommonConstant.TOKEN_PREFIX;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${integration.http.retry.max-attempts}")
    private int maxAttempts;

    @Value("${integration.http.retry.duration}")
    private int duration;

    @Value("${integration.http.open-ai.api-key}")
    private String openAIApiKey;

    @Bean
    @Qualifier("openAIWebClient")
    public WebClient openAIWebClient() {
        return WebClient
                .builder()
                .filter(retryFilter())
                .filter((request, next) -> next.exchange(ClientRequest.from(request)
                        .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + openAIApiKey)
                        .header("OpenAI-Beta", "assistants=v2")
                        .build()))
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, this::clientExceptionResponse)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, this::serverExceptionResponse)
                .build();
    }

    private ExchangeFilterFunction retryFilter() {
        return (request, next) -> next.exchange(request)
                .retryWhen(Retry.fixedDelay(maxAttempts, Duration.ofSeconds(duration))
                        .doAfterRetry(retrySignal -> log.info("RETRYING FOR ULR - {}", request.url()))
                        .filter(ServerResponseException.class::isInstance)
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            throw new ServiceException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
                        }));
    }

    private Mono<? extends Exception> clientExceptionResponse(ClientResponse response) {
        return response
                .bodyToMono(String.class)
                .map(body -> new ClientResponseException(response.statusCode(), body));
    }

    private Mono<? extends Exception> serverExceptionResponse(ClientResponse response) {
        return response
                .bodyToMono(String.class)
                .map(body -> new ServerResponseException(response.statusCode(), body));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiRequestInterceptor());
    }
}
