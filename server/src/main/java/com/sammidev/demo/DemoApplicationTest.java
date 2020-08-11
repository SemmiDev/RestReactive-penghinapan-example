package com.sammidev.demo;

import com.sammidev.demo.entities.Penghinapan;
import com.sammidev.demo.repository.PenghinapanRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.http.MediaType.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    PenghinapanRepo repo;

    @Test
    public void contextLoads() {
        Penghinapan penghinapan = new Penghinapan("sample penghinapan");
        webTestClient.post().uri("/penghinapan")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .body(Mono.just(penghinapan), Penghinapan.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("sample penghinapan");
    }

    @Test
    public void testGetAllPenhinapan() {
        webTestClient.get().uri("/penghinapan")
                .accept(APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8)
                .expectBodyList(Penghinapan.class);
    }

    @Test
    public void testUpdatePenghinapan() {
        Penghinapan penghinapan = repo.save(new Penghinapan("Initial penhinpan")).block();

        Penghinapan newPenghinapan = new Penghinapan("Updated penghinpan");

        webTestClient.put()
                .uri("/penghinapan/{id}", Collections.singletonMap("id", penghinapan.getId()))
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .body(Mono.just(newPenghinapan), Penghinapan.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.name").isEqualTo("Updated penghinpan");
    }

    @Test
    public void testDeletePenghinapan() {
        Penghinapan penghinapan = repo.save(new Penghinapan("To be deleted")).block();

        webTestClient.delete()
                .uri("/penghinapan/{id}", Collections.singletonMap("id",  penghinapan.getId()))
                .exchange()
                .expectStatus().isOk();
    }

}
