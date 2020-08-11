package com.sammidev.demo.client;

import com.sammidev.demo.model.Penghinapan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PenghinapanWebClient {
    private WebClient client;


    public PenghinapanWebClient(WebClient.Builder client) {
        this.client = client.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("http://localhost:8080/penghinapan").build();
    }

    public Mono<Penghinapan> getById(String id) {
        return client.get().uri("/{id}", id)
                .retrieve().bodyToMono(Penghinapan.class);
    }

    public Flux<Penghinapan> getAll() {
        return client.get().retrieve().bodyToFlux(Penghinapan.class);
    }

    public Mono<Penghinapan> save(Penghinapan penghinapan) {
        return client.post()
                .body(BodyInserters.fromObject(penghinapan))
                .exchange().flatMap(clientResponse -> clientResponse.bodyToMono(Penghinapan.class));
    }

    public Mono<Penghinapan> update(String id, Penghinapan penghinapan) {

        Mono<Penghinapan> hotelMono = Mono.just(penghinapan);
        Mono<Penghinapan> hotelMonoUpdate = client.put().uri("/{id}",id).accept(MediaType.APPLICATION_JSON)
                .body(hotelMono, Penghinapan.class).retrieve().bodyToMono(Penghinapan.class);

        return hotelMonoUpdate;
    }

    public Mono<Void> delete(String id) {
        return client.delete()
                .uri("/{id}", id).retrieve().bodyToMono(Void.class);
    }
}
