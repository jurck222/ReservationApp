package com.example.reservation.reservations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping(path = "api/v1/reservation")
public class ReservationController {
    Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("reservations", reservationService.getReservations());
        return "index";
    }

    @GetMapping("/addReservation")
    public String addReservation(Model model) {
        return "addReservation";
    }

    @PostMapping("/processForm")
    public String processForm(@RequestParam String title,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservationTime,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservationEndTime, Model model) {
        var reservation = new Reservation(reservationTime, reservationEndTime, title);
        try {
            reservationService.addNewReservation(reservation);
            return "redirect:/api/v1/reservation";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "addReservation";
        } catch (Exception e) {
            model.addAttribute("error", "Something went wrong please try again later.");
            logger.error(e.getMessage());
            return "addReservation";
        }
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationService.getReservations();
    }

    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id, Model model) {
        try {
            reservationService.deleteReservation(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "redirect:/api/v1/reservation";
    }

    @PostMapping
    public void addNewReservation(@RequestBody Reservation reservation) {
        try {
            reservationService.addNewReservation(reservation);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long id) {
        try {
            reservationService.deleteReservation(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @PutMapping(path = "{reservationId}")
    public void updateReservation(
            @PathVariable("reservationId") Long id,
            @RequestParam(required = false) LocalDateTime newStartTime,
            @RequestParam(required = false) LocalDateTime newEndTime,
            @RequestParam(required = false) String newTitle) {
        try {
            reservationService.updateReservation(id, newStartTime, newEndTime, newTitle);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

