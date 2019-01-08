package com.spring.study02;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class MonoTest {
    public static void main(String[] args) throws InterruptedException {
//        Mono.just("T").delayElement(Duration.ofSeconds(2)).log().flatMap(m -> {
//            System.out.println(m);
//            return Mono.just(m);
//        }).block();

        Flux.just("1", "2", "3", "4", "5")
                .log()
                .map(m -> {
                    System.out.println(m);
                    return m;
                }).subscribeOn(Schedulers.parallel()).subscribe();
        System.out.println("END");

        Thread.sleep(3000);
    }
}
