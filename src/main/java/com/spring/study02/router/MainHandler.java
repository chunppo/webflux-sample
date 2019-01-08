package com.spring.study02.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MainHandler {

    @Autowired
    private MainService mainService;

    public Mono<ServerResponse> githubUsersHandler(ServerRequest serverRequest) {
        Mono<List<GithubUsersNames>> join = mainService.join();
        System.out.println("TTTTTTTTTTTTTTTTTTTT");
        return ServerResponse.ok().body(join, new ParameterizedTypeReference<List<GithubUsersNames>>() {});
    }
}
