package com.spring.study02.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public Mono<List<GithubUsersNames>> join() {
        List<GithubUsersNames> result = new ArrayList<>();

        return this.githubUsers()
                .map(githubUsers -> {
                    Mono.fromSupplier(githubUsers)
                    Flux.fromIterable(githubUsers)
                            .doOnNext(a -> {
                                return this.githubUsersNames(a.getLogin())
                                        .map(z -> {
                                            GithubUsersNames bean = new GithubUsersNames();

                                            bean.setAvatarUrl(z.getAvatarUrl());
                                            result.add(bean);

                                            return result;
                                        });
                            });

                    return result;
                });
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
