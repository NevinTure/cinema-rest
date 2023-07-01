package rest.cinema.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CinemaStatistics {

    @JsonProperty("current_income")
    private int currentIncome;
    @JsonProperty("number_of_available_seats")
    private int numberOfAvailableSeats;
    @JsonProperty("number_of_purchased_tickets")
    private int numberOfPurchasedSeats;

    public CinemaStatistics(int currentIncome, int numberOfAvailableSeats, int numberOfPurchasedSeats) {
        this.currentIncome = currentIncome;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.numberOfPurchasedSeats = numberOfPurchasedSeats;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public int getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public int getNumberOfPurchasedSeats() {
        return numberOfPurchasedSeats;
    }

    public void setNumberOfPurchasedSeats(int numberOfPurchasedSeats) {
        this.numberOfPurchasedSeats = numberOfPurchasedSeats;
    }
}
