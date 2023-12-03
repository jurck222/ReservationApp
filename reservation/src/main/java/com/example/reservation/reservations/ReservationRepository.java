package com.example.reservation.reservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r " +
            "WHERE :newStartTime <= r.reservation_end AND :newEndTime >= r.reservation_time")
    List<Reservation> findConflictingReservations(
            @Param("newStartTime") LocalDateTime newStartTime,
            @Param("newEndTime") LocalDateTime newEndTime
    );
    @Query("SELECT r FROM Reservation r " +
            "WHERE :newStartTime <= r.reservation_end AND :newEndTime >= r.reservation_time AND r.id != :Id")
    List<Reservation> findConflictingReservationsWhenUpdating(
            @Param("Id") Long id,
            @Param("newStartTime") LocalDateTime newStartTime,
            @Param("newEndTime") LocalDateTime newEndTime
    );


}
