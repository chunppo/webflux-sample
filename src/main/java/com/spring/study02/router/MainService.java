package com.spring.study02.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.reactivestreams.Subscription;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class MainService {

    private static WebClient webClient;

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter(mapper);
        return converter;
    }

    public MainService(WebClient.Builder webBuilder) {
        this.webClient = webBuilder.baseUrl("https://api.github.com").build();
    }

    public Mono<String> join(Mono<UserParam> userParamMono) {

        return userParamMono
                .map(a -> {
                    return a.getUserName();
                });
//        return this.preGithubUsers();
    }

    public Mono<String> preGithubUsers() {
        System.out.println("Call preGithubUsers");
        System.out.println(Thread.currentThread().getName());
        return Mono.just("ABCDEF");
    }

    public Mono<List<GithubUsers>> githubUsers() {
        System.out.println("Call githubUsers");
        return this.webClient.get().uri("/users").retrieve().bodyToMono(new ParameterizedTypeReference<List<GithubUsers>>() {});
    }

    public Mono<GithubUsersNames> githubUsersNames(String url) {
        System.out.println("Call githubUsersNames");
        return this.webClient.get().uri("/users/" + url).retrieve().bodyToMono(new ParameterizedTypeReference<GithubUsersNames>() {});
    }
}
