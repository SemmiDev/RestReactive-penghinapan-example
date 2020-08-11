package com.sammidev.demo;

import com.sammidev.demo.client.PenghinapanWebClient;
import com.sammidev.demo.model.Penghinapan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	PenghinapanWebClient client;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("----- all penghinapans -----");
		client.getAll().subscribe();




		System.out.println("----- save penghinapans -----");

		Penghinapan penghinapan = new Penghinapan();
		penghinapan.setName("TEST NAMA PENGHINAPAN");
		penghinapan.setAddress("TEST ADDRESS");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM");
		Date testDate = simpleDateFormat.parse("2020-17-08");
		penghinapan.setCreatedAt(testDate);
		penghinapan.setId(client.save(penghinapan).block().getId());



		System.out.println("----- get id penghinapans -----");
		client.getById(penghinapan.getId()).subscribe();


		System.out.println("----- update penghinapans -----");


		Penghinapan penghinapanUpdate = new Penghinapan();
		penghinapanUpdate.setId(penghinapan.getId());
		penghinapanUpdate.setName("update name");
		penghinapanUpdate.setAddress("update address");
		client.update(penghinapan.getId(), penghinapanUpdate).subscribe();

		System.out.println("### Delete a penghinapan");
		client.delete(penghinapan.getId()).subscribe();
	}
}