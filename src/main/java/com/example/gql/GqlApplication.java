package com.example.gql;

import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.Persistence;

@SpringBootApplication
public class GqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GqlApplication.class, args);
    }


    @Bean
    public Mutiny.SessionFactory sessionFactory() {
        return Persistence.createEntityManagerFactory("mainPU")
                .unwrap(Mutiny.SessionFactory.class);
    }
}
