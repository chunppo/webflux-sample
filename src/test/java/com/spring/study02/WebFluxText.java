package com.spring.study02;

import com.spring.study02.router.MainRouter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest
public class WebFluxText {

    @Autowired
    ApplicationContext context;

    @Autowired
    private WebTestClient rest;

    @Before
    public void setup() {
//        this.rest = WebTestClient
//                .bindToApplicationContext(this.context)
//                // add Spring Security test Support
////                .apply(springSecurity())
//                .configureClient()
////                .filter(basicAuthentication())
//                .build();
    }


    @Test
    public void test() {
        rest.get().uri("/api/main_info")//
                .exchange()
//                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("mainHandler");
    }
}
