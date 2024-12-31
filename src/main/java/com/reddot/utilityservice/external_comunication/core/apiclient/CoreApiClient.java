package com.reddot.utilityservice.external_comunication.core.apiclient;

import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.custom_exceptions.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CoreApiClient {

    private final WebClient webClient;

    @Value("${error.common_error_message}")
    private String commonErrorMessage;

    public CoreApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public <E, T> T callPost(String url, E body, Class<T> responseType) {
        try {
            return webClient.post()
                    .uri(url)
                    .body(BodyInserters.fromValue(body))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, res ->
                            res.bodyToMono(ErrorResponse.class)
                                    .flatMap(error ->
                                            Mono.error(new ApplicationException(new CustomError(error.getCode(), error.getMessage())))
                                    ))
                    .onStatus(HttpStatus::isError, clientResponse -> {
                        throw new ApplicationException(new CustomError("400", commonErrorMessage));
                    })
                    .bodyToMono(responseType)
                    .block();
        } catch (ApplicationException e){
            throw e;
        }
        catch (Exception e) {
            log.debug("{}", e);
            throw new ApplicationException(new CustomError("400", commonErrorMessage));
        }
    }

    public <T> T callGet(String url, Class<T> responseType) {
        try {
             return webClient.get()
                    .uri(url)
                    .header("AUTH-KEY", "40WiKUZe3cH826NPsypgfuKJvKSZBdkX")
                    .header("STK-CODE", "FSIBL")
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, res ->
                            res.bodyToMono(ErrorResponse.class)
                                    .flatMap(error -> Mono.error(new ApplicationException(new CustomError(error.getCode(), error.getMessage())))))
                    .onStatus(HttpStatus::isError, clientResponse -> {
                        throw new ApplicationException(new CustomError("400", commonErrorMessage));
                    })
                    .bodyToMono(responseType)
                    .block();

        }
        catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            log.debug("{}", e);
            throw new ApplicationException(new CustomError("400", commonErrorMessage));
        }
    }

}

