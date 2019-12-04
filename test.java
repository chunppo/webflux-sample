package com.chunppo.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
@EnableWebFlux
@RestController
public class ReactiveApplication {

    public static void main(String[] args) {
        System.setProperty("reactor.netty.ioWorkerCount", "100");
        SpringApplication.run(ReactiveApplication.class, args);
    }

    @GetMapping("/detail/{type}/{key}")
    public Mono<Integer> getFlux(@PathVariable String type, @PathVariable Integer key) throws InterruptedException {
        Thread.sleep(1000);
        log.info("### type: " + type + " - value: " + key + " - Thread Name: " + Thread.currentThread().getName());
        return Mono.just(key);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @Order(1)
    ApplicationRunner applicationRunnerSync() {
        return (args) -> {
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            for(int i : Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)) {
                restTemplate.exchange("http://localhost:8080/detail/sync/" + i, HttpMethod.GET, null, String.class);
            }

            stopWatch.stop();
            log.info("### Sync Elapsed : " + stopWatch.getTotalTimeMillis());
        };
    }

    @Bean
    @Order(2)
    ApplicationRunner applicationRunner() {
        return (args) -> {
            final WebClient webClient = WebClient.create();
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    .flatMap(t -> webClient.get()
                            .uri("http://localhost:8080/detail/async/{key}", t)
                            .retrieve()
                            .bodyToMono(String.class))
                    .doFinally(b -> {
                        stopWatch.stop();
                        log.info("### Elapsed : " + stopWatch.getTotalTimeMillis());
                    })
                    .subscribe();
        };
    }
}


