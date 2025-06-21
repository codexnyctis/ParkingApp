/**
 * PARKING SPOT CLASS: This is a class to create a single parking spot object. This document also contains parking spot related methods.
 * Methods: Parking spot constructor, getter methods for spot id, car parked on a spot, and duration of parking
 * methods to park a car to a spot,remove a car from spot,checking if the spot is already taken.
 * @author Nur Sarikaya <104520751>
 * @file version 1.3 - Java version 21.0.2
 * @date 14/04/2024
 * References: https://ioflood.com/blog/java-duration/ (How to show duration in Java)
 */
import java.time.Duration;
import java.time.LocalDateTime;
/**
 * Parking Spot can have an ID and a car parked on it. Therefore the class needs two variables given below.
 */
class ParkingSpot {
    private String spotId;
    private Car parkedCar; //parkedCar from the Car class

    /**
     * Constructs a ParkingSpot object with spot ID.
     *
     * @param spotId the ID of the parking spot
     */
    public ParkingSpot(String spotId) {
        this.spotId = spotId;
        this.parkedCar = null; //when created, the spot is empty
    }

    /**
     * Returns ID of the spot.
     *
     * @return ID of the spot (string)
     */
    public String getSpotId() {
        return spotId;
    }

    /**
     * Method to check if the spot is taken or not.
     *
     * @return true if the spot is taken, false if not. (boolean)
     */
    public boolean spotTaken() {
        if (parkedCar == null) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Method to change parkedCar variable value as car object to park a car in a spot.
     * 
     * @param car the car is to be parked
     */
    public void parkCar(Car car) {
        if (spotTaken()) {
            return;
        }
        this.parkedCar = car; //if the spot is available car becomes the parkedCar 
    }

    /**
     * Method to return the parkedCar value.
     * 
     * @return the car object if  (Car)
     */
    public Car getParkedCar() {
        return parkedCar;
    }
    
    /**
     * Method to clear parkedCar variable to remove the car from the spot.
     */
    public void removeCar() {
        this.parkedCar = null;
    }
    
    /**
     * Method to calculate and return the duration of a car parked on a spot.
     * 
     * @return park duration as hours + minutes + seconds, No car parked yet is the spot is empty
     */
    public String getParkDuration() {
        if (spotTaken()) {
            LocalDateTime parkedTime = parkedCar.getParkedTime(); //the time car parked
            LocalDateTime currentTime = LocalDateTime.now();  //now

            long durationSecs = Duration.between(parkedTime, currentTime).getSeconds(); //difference between now and time car parked in seconds

            long hours = durationSecs / 3600; //as hours
            long mins = (durationSecs % 3600) / 60; //as minutes
            long secs = durationSecs % 60; // as seconds

            return hours + " hours " + mins + " minutes " + secs + " seconds"; //return format hours+ minutes + seconds
        }
        return "No car parked yet";
    }

    /**
     * Method to return parked spot information as a string (especially for list all spots function)
     * 
     * @return spot ID, car registration number, car make, parking duration with a message saying that the spot is taken if the spot is taken,
     * otherwise spot ID and a message saying the spot is available
     */
    @Override
    public String toString() {
        if (spotTaken()) {
            Car parkedCar = getParkedCar();
            return "Spot: " + getSpotId() +
                    ", Registration: " + parkedCar.getRegistrationNo() +
                    ", Make: " + parkedCar.getCarMake() +
                    ", Model: " + parkedCar.getCarModel() +
                    ", Year: " + parkedCar.getYear() +
                    ", Parking Duration: " + getParkDuration();
        } else {
            return "Spot: " + getSpotId() + ", Empty";
        }
    }
    
}