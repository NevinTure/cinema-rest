package rest.cinema.controller;


import rest.cinema.exception.IncorrectPasswordException;
import rest.cinema.exception.SeatAlreadyPurchasedException;
import rest.cinema.exception.WrongTokenException;
import rest.cinema.model.CinemaHall;
import rest.cinema.model.CinemaStatistics;
import rest.cinema.model.Seat;
import rest.cinema.model.TicketInfo;
import rest.cinema.service.CinemaHallService;
import rest.cinema.service.TicketInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class CinemaController {

    private final CinemaHallService service;
    private final CinemaHall cinemaHall;

    private final TicketInfoService ticketService;

    @Value("${password}")
    private String PASSWORD;

    private final ConcurrentHashMap<UUID, Seat> ticketsInfo;

    public CinemaController(CinemaHallService service, TicketInfoService ticketService) {
        this.service = service;
        this.cinemaHall = service.createCinemaHall();
        this.ticketService = ticketService;
        ticketsInfo = new ConcurrentHashMap<>();
    }


    @GetMapping("/seats")
    public CinemaHall getCinemaHallSeats() {
        return cinemaHall;
    }

    @PostMapping("/purchase")
    public TicketInfo purchase(@Valid @RequestBody Seat seat) {
        seat = service.getSeat(seat);

        if (service.checkIsSeatPurchased(cinemaHall, seat)) {
            throw new SeatAlreadyPurchasedException("The ticket has been already purchased!");
        } else {
            service.purchaseSeat(cinemaHall, seat);
            TicketInfo info = ticketService.createTicketInfo(seat);
            ticketsInfo.put(info.getToken(), seat);
            return info;
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Seat>> returnTicket(@RequestBody Map<String, Object> token) {
        UUID id = UUID.fromString((String) token.get("token"));

        if(ticketsInfo.containsKey(id)) {
            Seat seat = ticketsInfo.remove(id);
            service.returnSeat(cinemaHall, seat);
            return new ResponseEntity<>(Map.of("returned_ticket", seat), HttpStatus.OK);
        } else {
            throw new WrongTokenException("Wrong token!");
        }
    }

    @GetMapping("/stats")
    public CinemaStatistics getStatistics(@RequestParam(value = "password", required = false) String password) {

        if(Objects.equals(password, PASSWORD)) {
            return service.getCinemaStatistics(cinemaHall);
        } else {
            throw new IncorrectPasswordException("The password is wrong!");
        }
    }
}
