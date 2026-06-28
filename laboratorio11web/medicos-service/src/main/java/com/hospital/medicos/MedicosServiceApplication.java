package com.hospital.medicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MedicosServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedicosServiceApplication.class, args);
    }
}
