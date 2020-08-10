package com.sammidev.demo.repository;

import com.sammidev.demo.entities.Penghinapan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenghinapanRepo extends ReactiveMongoRepository<Penghinapan, String> {

}
