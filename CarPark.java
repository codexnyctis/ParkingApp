/**
 * CAR PARK CLASS: This is a class for creating a parking lot objects and managing parking spots in the CarPark, 
 * and it contains lists of parking spots and methods related to the parking lot.
 * Methods: Methods for parking a car to a specific spot, removing car from that spot, finding a car by registration number, finding a car by make,
 * listing all the spots, reseting (cleaning) all the spots, check if the registration number is in a spot.
 * @author Nur Sarikaya <104520751>
 * @file version 1.3 - Java version 21.0.2
 * @date 14/04/2024
 * References: https://www.w3schools.com/java/java_date.asp (formatting date and time)
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class CarPark {
    private List<ParkingSpot> parkingSpots; // The list of all parking spots in the car park

    /**
     * Constructor method for creating a CarPark object with empty list of parking spots.
     */
    public CarPark() { 
        parkingSpots = new ArrayList<>();  
    }

    /**
     * Method to add a new parking spot to the CarPark using the spot ID.
     * 
     * @param a new spot id
     */
    public void addASpot(String spotId) {
        ParkingSpot spot = new ParkingSpot(spotId);
        parkingSpots.add(spot);
    }

    /**
     * Method to delete an existing spot in the CarPark using a spot ID.
     * 
     * @param an existing spot id
     */
    public void deleteASpot(String spotId) {
        for (int i = 0; i < parkingSpots.size(); i++) { //loops through the list of parking spots
            ParkingSpot spot = parkingSpots.get(i); // returns the spot at each index
            if (spot.getSpotId().equals(spotId)) { //if provided spotID matches the ID of spot a any index
                parkingSpots.remove(i); //removes that index
                break;
            }
        }
    }

    /**
     * Method to check if such spot is existed or not.
     * 
     * @param spot ID to check if it exists
     * @return true if exist, false if not
     */
    public boolean spotExisted(String spotId) {
        for (ParkingSpot spot : parkingSpots) { //for each spot
            if (spot.getSpotId().equals(spotId)) { //if the gotten id matches the entered id
                return true; //it exists
            }
        }
        return false; //it doesn't exist
    }

    /**
     * Method to check with certain ID is occupied by a car or not.
     * 
     * @param existing spot ID
     * @return true if occupied, false if not
     */
    public boolean spotOccupied(String spotId) {
        for (ParkingSpot spot : parkingSpots) { //for each spot
            if (spot.getSpotId().equals(spotId)) { //if the returned id matches the entered id (if the spot exists)
                return spot.spotTaken(); // spotTaken method is called - if returns true occupied
            }
        }
        return false; //not occupied or not exists
    }

    /**
     * Method to park car in a specific spot with ID.
     * 
     * @param spotId id of the spot to park a car
     * @param car  car object to park on the spot of provided id.
     */
    public void placeCarToSpot(String spotId, Car car) { 
        ParkingSpot matchingSpot = null; //a new variable initialised as null to store matching spot
        for (ParkingSpot spot : parkingSpots) { //for each spot
            if (spot.getSpotId().equals(spotId)) { //check if any spot matching with entered spotID
                matchingSpot = spot; //If so macthingSpot variable stores the spot data
                break;
            }
        }
        
        if (matchingSpot != null) { //if the variable is not null
            LocalDateTime parkedTime = LocalDateTime.now(); //set the parking time
            car.setParkedTime(parkedTime); // also set the same time to the car object
            System.out.println("\nParking time: " + formatDateTime(parkedTime)); //print the parking time
            matchingSpot.parkCar(car); //place the car to a given spotID by recalling parkCar method from ParkingSpot class
        } else {
            System.out.println("\nInvalid spot ID: " + spotId); //matchingSpot=null there is no spot with given ID.
        }
    }

        /**
     * Method to remove a parked car from the spot
     * 
     * @param spotId id of the parking spot where the car is parked.
     */
    public void removeCarFromSpot(String spotId) {
        for (int i = 0; i < parkingSpots.size(); i++) {  //loop
            ParkingSpot spot = parkingSpots.get(i); 
            if (spot.getSpotId().equals(spotId)) { //if spotId in the list matches with the entered one
                spot.removeCar(); //remove the car on that spot
                return;
            }
        }
        System.out.println("Invalid spot ID: " + spotId); //Invalid id message
    }   

    /**
     * Method to check if a car with given registration number exists.
     * 
     * @param registrationNo registration number of a car (eg.A1234)
     * @return if a car with such registration number is parked in a spot, returns true; otherwise false
     */
    public boolean carExists(String registrationNo) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.spotTaken() && spot.getParkedCar().getRegistrationNo().equals(registrationNo)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method to search car by using the registration number
     * 
     * @param registrationNo car registration number to search
     * @return spotId of the spot where a car is parked 
     */
    public String searchCarByRegistration(String registrationNo) {
        for (ParkingSpot spot : parkingSpots) { //for each spot
            if (spot.spotTaken() && spot.getParkedCar().getRegistrationNo().equals(registrationNo)) { //if all conditions are met
                return spot.getSpotId(); //return spotId to a spot
            }
        }
        return null; //otherwise null
    }

    /**
     * Method the bring list of car objects using the car make input 
     * 
     * @param make make/brand of the car
     */
    public void searchCarByMake(String make) {
        System.out.println("Cars with make " + make + " parked in the car park:");
        boolean carFound = false; //storing the boolean of car exists or not
    
        for (ParkingSpot spot : parkingSpots) {
            if (spot.spotTaken() && spot.getParkedCar().getCarMake().equalsIgnoreCase(make)) {
                System.out.println("Spot: " + spot.getSpotId() +
                        ", Registration: " + spot.getParkedCar().getRegistrationNo() +
                        ", Make: " + spot.getParkedCar().getCarMake() +
                        ", Model: " + spot.getParkedCar().getCarModel() +
                        ", Year: " + spot.getParkedCar().getYear() +
                        ", Parking Duration: " + spot.getParkDuration());
                carFound = true; //all conditions are met car with given make found
            }
        }
    
        if (!carFound) {
            System.out.println("There is no car with " + make + " make in the car park."); //meaningful user feedback for if (!carFound)
        }
    }

    /**
     * Method to list all parking spots on the list
     */
    public void listSpots() {
        int allSpots = parkingSpots.size(); //number of all spots
        int occupiedSpots = 0; 
        for (ParkingSpot spot : parkingSpots) { //for each spot
            if (spot.spotTaken()) { //if spot taken
                occupiedSpots++; //increment the number of occupied spots
            }
        }
        int emptySpots = allSpots - occupiedSpots; 

        System.out.println("\nList of all parking spots:");
        for (ParkingSpot spot : parkingSpots) { //for each spot
            System.out.println(spot.toString()); //print spot info
        }

        System.out.println("\nSummary:");
        System.out.println("All spots: " + allSpots);
        System.out.println("Occupied spots: " + occupiedSpots);
        System.out.println("Empty spots: " + emptySpots);
    }
    
    /**
     * Formatting the date and time as a String
     * 
     * @param dateTime object of LocalDateTime
     * @return formatted time as yyyy-MM-dd HH:mm:ss
     */
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
    
    /**
     * Method to remove all the cars from the spots
     */
    public void resetCarPark() {
        for (ParkingSpot spot : parkingSpots) {
            spot.removeCar();
        }
    }
    
        public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
}