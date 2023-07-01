package rest.cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TicketInfo {

    @JsonProperty("token")
    private UUID token;

    @JsonProperty("ticket")
    private Seat seat;

    public TicketInfo(Seat seat, UUID token) {
        this.seat = seat;
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
