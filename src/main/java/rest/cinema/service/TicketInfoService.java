package rest.cinema.service;

import rest.cinema.model.Seat;
import rest.cinema.model.TicketInfo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketInfoService {

    public TicketInfo createTicketInfo(Seat seat) {
        return new TicketInfo(seat, UUID.randomUUID());
    }
}
