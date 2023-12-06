package com.example.reservation.reservations;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table
public class Reservation {
    @Id
    @SequenceGenerator(
            name = "reservation_sequence",
            sequenceName = "reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_sequence"
    )
    private Long id;
    @NotNull
    private LocalDateTime reservation_time;
    @NotNull
    private LocalDateTime reservation_end;
    @NotNull
    private String title;

    public Reservation() {
    }

    public Reservation(LocalDateTime reservation_time, LocalDateTime reservation_end, String title) {
        this.reservation_time = reservation_time;
        this.reservation_end = reservation_end;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(LocalDateTime reservation_time) {
        this.reservation_time = reservation_time;
    }

    public LocalDateTime getReservation_end() {
        return reservation_end;
    }

    public void setReservation_end(LocalDateTime reservation_end) {
        this.reservation_end = reservation_end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservation_time=" + reservation_time +
                ", reservation_end=" + reservation_end +
                ", title='" + title + '\'' +
                '}';
    }
}
