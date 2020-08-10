package com.sammidev.demo.controller;

import com.sammidev.demo.entities.Penghinapan;
import com.sammidev.demo.exception.PenghinapanNotFoundException;
import com.sammidev.demo.repository.PenghinapanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
import static reactor.core.publisher.Flux.interval;

@RestController
@RequestMapping("/penghinapan")
public class PenghinapanController {

    @Autowired
    private PenghinapanRepo repository;

    // get all penghinapans
    @GetMapping()
    public Flux<Penghinapan> getAllPenghinapans() {
        return repository.findAll();
    }

    // create
    @PostMapping()
    public Mono<Penghinapan> createPenghinapan(@Valid @RequestBody Penghinapan penghinapan) {
        return repository.save(penghinapan);
    }

    // get hotel by id
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Penghinapan>> getPenghinapaById(@PathVariable(value = "id") String penghinapanId) {
        return repository.findById(penghinapanId)
                .switchIfEmpty(Mono.error(new PenghinapanNotFoundException("penghinapan id not found")))
                .map(ResponseEntity::ok);
    }

    // update
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Penghinapan>> updatePenghinapan(
            @PathVariable(value = "id") String penghinapanId,
            @Valid @RequestBody Penghinapan penghinapan) {

        return repository.findById(penghinapanId)
                .flatMap(existingPenghinapan -> {
                    existingPenghinapan.setName(penghinapan.getName());
                    existingPenghinapan.setAddress(penghinapan.getAddress());
                    existingPenghinapan.setCreatedAt(penghinapan.getCreatedAt());
                    return repository.save(existingPenghinapan);
                })
                .map(updatedHotel -> new ResponseEntity<>(updatedHotel, OK))
                .defaultIfEmpty(new ResponseEntity<>(NOT_FOUND));
    }


    // delete by id
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePenghinapan(@PathVariable(value = "id") String penghinapanId) {
        return repository.findById(penghinapanId)
                .flatMap(existingPenghinapan ->
                        repository.delete(existingPenghinapan)
                .then(Mono.just(new ResponseEntity<Void>(OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(NOT_FOUND));
    }

    // stream
    @GetMapping(value = "/stream", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<Penghinapan> streamAllTweets() {
        return repository.findAll();
    }
}
