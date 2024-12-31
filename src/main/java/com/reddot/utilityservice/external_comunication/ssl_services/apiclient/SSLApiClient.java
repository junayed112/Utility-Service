package com.reddot.utilityservice.external_comunication.ssl_services.apiclient;

import com.google.gson.Gson;
import com.reddot.utilityservice.custom_exceptions.ApplicationException;
import com.reddot.utilityservice.custom_exceptions.CustomError;
import com.reddot.utilityservice.custom_exceptions.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@Slf4j
public class SSLApiClient {

    private final WebClient webClient;

    @Value("${error.common_error_message}")
    private String commonErrorMessage;

    public SSLApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public <E,T> T callPost(String url, E body, Class<T> responseType, MultiValueMap<String,String> headersMap) {
        try {
             return  webClient.post()
                    .uri(url)
                     .headers(
                             httpHeaders -> {
                                 httpHeaders.addAll(headersMap);
                             }
                     )
                    .body(BodyInserters.fromValue(body))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, res ->
                            res.bodyToMono(ErrorResponse.class)
                                    .flatMap(error -> Mono.error(new ApplicationException(new CustomError(error.getCode(), error.getMessage())))))
                    .onStatus(HttpStatus::isError, clientResponse -> {
                        throw new ApplicationException(new CustomError("400", commonErrorMessage));
                    })
                    .bodyToMono(responseType)
                    .block();
        }catch (ApplicationException e){
            log.debug("{}",e);
            throw e;
        }
        catch (Exception e){
            log.debug("{}",e);
            throw new ApplicationException(new CustomError("400", commonErrorMessage));
        }
    }

    public<T> T callGet(String url, Class<T> responseType,MultiValueMap<String,String> headers) {
        try {
            String response =  webClient.get()
                    .uri(url)
                    .headers(
                            httpHeaders -> {
                                httpHeaders.addAll(headers);
                            }
                    )
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, res ->
                            res.bodyToMono(ErrorResponse.class)
                                    .flatMap(error -> Mono.error(new ApplicationException(new CustomError(error.getCode(), error.getMessage())))))
                    .onStatus(HttpStatus::isError, clientResponse -> {
                        throw new ApplicationException(new CustomError("400", commonErrorMessage));
                    })
                    .bodyToMono(String.class)
                    .block();
            return new Gson().fromJson(response,responseType);
        }catch (ApplicationException e){
            throw e;
        } catch (Exception e){
            log.debug("{}",e);
            throw new ApplicationException(new CustomError("400", commonErrorMessage));
        }
    }


    public void callGet(String url,MultiValueMap<String,String> headers) {
        try {
            String response =  webClient.get()
                    .uri(url)
                    .headers(
                            httpHeaders -> {
                                httpHeaders.addAll(headers);
                            }
                    )
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, res ->
                            res.bodyToMono(ErrorResponse.class)
                                    .flatMap(error -> Mono.error(new ApplicationException(new CustomError(error.getCode(), error.getMessage())))))
                    .onStatus(HttpStatus::isError, clientResponse -> {
                        throw new ApplicationException(new CustomError("400", commonErrorMessage));
                    })
                    .bodyToMono(String.class)
                    .block();
        }catch (ApplicationException e){
            throw e;
        } catch (Exception e){
            log.debug("{}",e);
            throw new ApplicationException(new CustomError("400", commonErrorMessage));
        }
    }

}

