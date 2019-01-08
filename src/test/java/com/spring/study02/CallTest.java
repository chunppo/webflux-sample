package com.spring.study02;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest
public class CallTest {

    @Autowired
    WebClient.Builder webClientBuilder;


    @Test
    public void call() {
        WebClient webClient = webClientBuilder.baseUrl("https://api.github.com").build();
//        WebClient webClient = webClientBuilder.baseUrl("https://www.naver.com").build();

        Mono<String> result1 = webClient.get().uri("/users").retrieve().bodyToMono(String.class);

        Mono<String> result2 = webClient.get().uri("/users").retrieve().bodyToMono(String.class);

        result1.doOnSuccess(t -> {
            System.out.println(t);
        }).subscribe();
    }

    //
//@Component
//class ResourceParsingFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
//        final HttpMethod httpMethod = request.method();
//        final String requestPath = request.path();
//
//        switch (httpMethod) {
//            case GET:
//            case PUT:
//            case DELETE:
//                return ServerResponse.ok().body(fromObject("sdfsdfsdf"));
//            default:
//                break;
//        }
//
//        return next.handle(request);
//    }
//
//}
//
//@Component
//class CustomHandlerFilter {
//
//    public HandlerFilterFunction<ServerResponse, ServerResponse> filter() {
//        return (request, next) -> {
//
//            System.out.println("CustomHandlerFilter");
//            return next.handle(request);
//        };
//    }
//}
//
//@Component
//@Slf4j
//class Handler {
//    private final WebClient webClient;
//
//    public Handler(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder
//                .baseUrl("https://www.naver.com")
//                .filter(logRequest())
//                .build();
//    }
//
//    private ExchangeFilterFunction logRequest() {
//        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
//            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
//            return Mono.just(clientRequest);
//        });
//    }
//
//    public Mono<ServerResponse> mainHandler(ServerRequest serverRequest) {
//
////        serverRequest.queryParam("size").map(Integer::parseInt).orElseThrow(() -> new IllegalArgumentException());
////        new ResponseStatusExceptionHandler()
//
////        this.webClient.get().uri("/").retrieve().bodyToMono();
////        int a = 1 / 0;
//        return ServerResponse.ok().body(fromObject("mainHandler"));
//    }
//
//    public Mono<ServerResponse> testHandler(ServerRequest serverRequest) {
//        return ServerResponse.ok().body(fromObject("testHandler"));
//    }
//}
//
//@Service
//class MainService {
//    public Mono<List<String>> getMonoMain() {
//        return getMonoFirstList()
//                .map(m -> {
//                })
//    }
//
//    public Mono<List<String>> getMonoFirstList() {
//        return Mono.just(Arrays.asList("test1", "test2", "test3", "test4"));
//    }
//
//    public Mono<List<String>> getMonoSecondList(String args) {
//        return Mono.just(Arrays.asList("test1" + args, "test2" + args, "test3" + args, "test4" + args));
//    }
//}
//
////@Configuration
//class GlobalHandler extends AbstractErrorWebExceptionHandler {
//
//    public GlobalHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
//        super(errorAttributes, resourceProperties, applicationContext);
//        super.setMessageWriters(serverCodecConfigurer.getWriters());
//        super.setMessageReaders(serverCodecConfigurer.getReaders());
//    }
//
//    @Override
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        System.out.println("CHUNPPO-ERROR : " + errorAttributes);
//        System.out.println("TTTTTTTTTTTTTTTT");
//        return route(all(), this::errorHandler);
//    }
//
//    private Mono<ServerResponse> errorHandler(ServerRequest serverRequest) {
//
//        System.out.println(serverRequest.headers());
//
//        Map<String, Object> errorAttributes = getErrorAttributes(serverRequest, false);
//        System.out.println(errorAttributes);
//        Throwable error = getError(serverRequest);
//        System.out.println(error);
//
//        return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject("ER"));
//    }
//}
}
