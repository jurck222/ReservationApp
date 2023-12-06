package com.example.reservation.reservations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void addNewReservation(Reservation reservation) {
        var reservationList = reservationRepository.findConflictingReservations(reservation.getReservation_time(), reservation.getReservation_end());
        if (!reservationList.isEmpty()) {
            throw new IllegalStateException("The room is already reserved for this time");
        }
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        var exists = reservationRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("reservation with id " + id + " does not exist");
        }
        reservationRepository.deleteById(id);
    }

    public Reservation findReservation(Long id) {
        var exists = reservationRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("reservation with id " + id + " does not exist");
        }
        var optional = reservationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalStateException("reservation with id " + id + " does not exist");
        }
        return optional.get();
    }

    @Transactional
    public void updateReservation(Long id, LocalDateTime newStartTime, LocalDateTime newEndTime, String newTitle) {
        var reservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalStateException("Reservation with id " + id + " does not exist"));
        if (newTitle != null && !newTitle.isEmpty() && !Objects.equals(reservation.getTitle(), newTitle)) {
            reservation.setTitle(newTitle);
        }
        if (newStartTime != null && newEndTime != null) {
            var reservationList = reservationRepository.findConflictingReservationsWhenUpdating(id, newStartTime, newEndTime);
            if (!reservationList.isEmpty()) {
                throw new IllegalStateException("The room is already reserved for this time");
            }
            reservation.setReservation_time(newStartTime);
            reservation.setReservation_end(newEndTime);
        }
    }
}
