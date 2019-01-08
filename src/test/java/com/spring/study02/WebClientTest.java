package com.spring.study02;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientTest {
    public static void main(String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create("https://www.naver.com");

        Mono<String> result = webClient.get().uri("/").retrieve().bodyToMono(String.class);

        result.doOnSuccess(t -> {
            System.out.println("A:" + t);
        }).subscribe(System.out::println);

        Thread.sleep(3000);
    }
}
