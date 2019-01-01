package com.spring.study02.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.sortByQualityValue;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MainRouter {

    @Autowired
    Handler handler;

    @Bean
    public RouterFunction<?> main() {
        return
                nest(path("/api"),
                        route(GET("/main_info").and(accept(APPLICATION_JSON)), handler::mainHandler)
                        .andRoute(GET("/second_info").and(accept(APPLICATION_JSON)), req -> ServerResponse.ok().body(fromObject("second")))
                        .andOther(
                                route(GET("/**"), req -> ServerResponse.ok().body(fromObject("other")))
                        )
                )
                .andOther(route(GET("/**"), req -> ServerResponse.ok().body(fromObject("totalOther"))));
    }
}

@Component
class Handler {
    public Mono<ServerResponse> mainHandler(ServerRequest serverRequest) {

//        new ResponseStatusExceptionHandler()

        int a = 1 / 0;
        return ServerResponse.ok().body(fromObject("mainHandler"));
    }
}

@Configuration
class GlobalHandler extends AbstractErrorWebExceptionHandler {

    public GlobalHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        System.out.println("CHUNPPO-ERROR : " + errorAttributes);
        System.out.println("TTTTTTTTTTTTTTTT");
        return route(all(), this::errorHandler);
    }

    private Mono<ServerResponse> errorHandler(ServerRequest serverRequest) {

        System.out.println(serverRequest.headers());

        Map<String, Object> errorAttributes = getErrorAttributes(serverRequest, false);
        System.out.println(errorAttributes);
        Throwable error = getError(serverRequest);
        System.out.println(error);

        return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject("ER"));
    }
}