package com.example.reservation.reservations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Configuration
public class ReservationConfig {
    @Bean
    CommandLineRunner commandLineRunner(ReservationRepository repository) {
        return args -> {
            Reservation event1 = new Reservation(
                    LocalDateTime.of(2023, Month.JANUARY, 5, 15, 15),
                    LocalDateTime.of(2023, Month.JANUARY, 5, 16, 15),
                    "sestanek");
            Reservation event2 = new Reservation(
                    LocalDateTime.of(2023, Month.JANUARY, 6, 15, 15),
                    LocalDateTime.of(2023, Month.JANUARY, 6, 16, 15),
                    "sestanek");
            repository.saveAll(List.of(event1, event2));
        };

    }
}
