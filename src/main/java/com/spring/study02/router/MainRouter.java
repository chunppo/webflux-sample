package com.spring.study02.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MainRouter {

    @Autowired
    private MainHandler mainHandler;

    @Bean
    public RouterFunction<?> main() {
        return
                nest(path("/api"),
                        route(POST("/githubUsers"), mainHandler::githubUsersHandler)
                )
                .andOther(route(GET("/**"), req -> ServerResponse.ok().body(fromObject("totalOther"))));
    }
}