# webflux-sample

http://wiki.sys4u.co.kr/pages/viewpage.action?pageId=7766994#id-%EC%97%B0%EC%8A%B5%EB%AC%B8%EC%A0%9C%EB%A1%9C%EB%B0%B0%EC%9B%8C%EB%B3%B4%EB%8A%94Reactor-2.Mono

Mono<String> stringMono = loadTestService.method01().subscriberContext(Context.of("CHUN", serverRequest));


@Component
class TraceIdFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("TraceIdFilter");
        return chain.filter(exchange).subscriberContext((Context context) ->
                context.put(ServerWebExchange.class, exchange)
        );
    }
}

    public Mono<String> method01() {

        Mono<String> just = Mono.just("A")
                .subscriberContext()
                .map(context -> {
                    ServerWebExchange chun = (ServerWebExchange) context.get(ServerWebExchange.class);
                    chun.getAttributes().put("1000", "name");
                    return chun.getRequest().getHeaders().toString();
                });

        return just;
    }
