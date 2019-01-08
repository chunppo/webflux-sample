package com.spring.study02;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Study02ApplicationTests {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
//        getName()
//                .zipWith(getNickname())
//                .map(t -> {
//                    return t.getT1() + " | " + t.getT2();
//                }).subscribeOn(Schedulers.parallel()).subscribe(System.out::println);
//
        Mono
                .zip(Mono.fromSupplier(() -> getName()), getNickname())
                .map(t -> {
                    return t.getT1()
                            .zipWith(getNickname())
                            .map(ad -> ad.getT1() + " - " + ad.getT2()).block();
//                    return t.getT1() + " | " + t.getT2();
                }).subscribeOn(Schedulers.parallel()).subscribe(System.out::println);
        System.out.println("END");
        System.out.println(System.currentTimeMillis() - start);

        Thread.sleep(8000);
    }

    private static Mono<String> getName() {

//        return Mono.fromSupplier(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//
//            return "chunppo";
//        });
        return Mono.just("chunppo");
    }

    private static Mono<String> getNickname() {
        return Mono.fromSupplier(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "geco";
        });
    }

}

