package rest.cinema.service;

import rest.cinema.model.CinemaHall;
import rest.cinema.model.CinemaStatistics;
import rest.cinema.model.Seat;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaHallService {


    public CinemaHall createCinemaHall() {
        CinemaHall cinemaHall = new CinemaHall(
                9,
                9,
                Collections.synchronizedList(new ArrayList<>())
        );
        fulfillCinemaHall(cinemaHall);
        return cinemaHall;
    }

    public void fulfillCinemaHall(CinemaHall cinemaHall) {
        for (int i = 1; i <= cinemaHall.getTotalRows(); i++) {
            for (int j = 1; j <= cinemaHall.getTotalRows(); j++) {
                Seat seat = new Seat(i,j, getPrice(i));
                cinemaHall.getAvailableSeats().add(seat);
            }
        }
    }

    public Seat getSeat(Seat seat) {
        seat.setPrice(getPrice(seat.getRow()));
        return seat;
    }

    private int getPrice(int row) {
        return row <= 4 ? 10 : 8;
    }

    public void purchaseSeat(CinemaHall cinemaHall, Seat seat) {
        int index = getIndex(cinemaHall, seat);
        seat.setPurchased(true);
        cinemaHall.getAvailableSeats().set(index, seat);
    }

    public boolean checkIsSeatPurchased(CinemaHall cinemaHall, Seat seat) {
        return cinemaHall.getAvailableSeats().get(getIndex(cinemaHall, seat)).isPurchased();
    }

    public void returnSeat(CinemaHall cinemaHall, Seat seat) {
        cinemaHall.getAvailableSeats().get(getIndex(cinemaHall, seat)).setPurchased(false);
    }

    public CinemaStatistics getCinemaStatistics(CinemaHall cinemaHall) {
        int income = 0;
        int size = cinemaHall.getAvailableSeats().size();
        int purchasedTickets = 0;
        List<Seat> seats = cinemaHall.getAvailableSeats();


        for(int i = 0; i < size; i++) {
            if (seats.get(i).isPurchased()) {
                income += seats.get(i).getPrice();
                purchasedTickets++;
            }
        }

        return new CinemaStatistics(income, size - purchasedTickets, purchasedTickets);
    }
//    public boolean validateSeat(CinemaHall cinemaHall, Seat seat) {
//        return seat.getRow() > cinemaHall.getTotalRows() ||
//                seat.getColumn() > cinemaHall.getTotalColumns() ||
//                seat.getRow() < 1 || seat.getColumn() < 1;
//    }

//    public CinemaHallDTO convertCinemaHallToDTO(CinemaHall cinemaHall) {
//        Set<Seat> set = new TreeSet<>(getComparator());
//        set.addAll(cinemaHall.getAvailableSeats().keySet());
//        return new CinemaHallDTO(
//                cinemaHall.getTotalRows(),
//                cinemaHall.getTotalColumns(),
//                set
//        );
//    }

    private int getIndex(CinemaHall cinemaHall, Seat seat) {
        return cinemaHall.getTotalRows() * (seat.getRow() - 1) + seat.getColumn() - 1;
    }

//    public Comparator<Seat> getComparator() {
//        return Comparator.comparing(Seat::getRow).thenComparing(Seat::getColumn);
//    }



}
