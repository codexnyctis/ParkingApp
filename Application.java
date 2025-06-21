/**
 * APPLICATION CLASS: This is the main class that contains main method and methods to handle user interactions, menu options, input validation. 
 * @author Nur Sarikaya <104520751>
 * @file version 1.3 - Java version 21.0.2
 * @date 14/04/2024
 * References: https://kevinsguides.com/guides/code/java/javaintro/ch13-trycatch 
 * https://www.youtube.com/watch?v=03-pqmQ5jkY (java.util.InputMismatchException)
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {
    private static CarPark carPark = new CarPark(); 
    private static Scanner scanner = new Scanner(System.in); 
    
    /**
     * Main method where the program starts to execute. It shows menu to performing some tasks.
     * 
     * @param args the command line inputs provided by the user
     */
    public static void main(String[] args) {
        boolean exit = false; //set to false
        while (!exit) { 
            showTheMenu();
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 9.");
                scanner.nextLine(); // Clear the invalid input from the scanner
                continue; // Restart the loop to prompt for input 
            }

            switch (choice) {
                case 1:
                    addParkingSpot();
                    break;
                case 2:
                    deleteParkingSpot();
                    break;
                case 3:
                    listAllSpots();
                    break;
                case 4:
                    resetCarPark();
                    break;
                case 5:
                    parkCar();
                    break;
                case 6:
                    findCarByRegistrationNo();
                    break;
                case 7:
                    findCarsByMake();
                    break;
                case 8:
                    removeCarByRegistrationNo();
                    break;
                case 9:
                    System.out.print("\nARE YOU SURE YOU WANT TO EXIT? (Y/N): "); //exit confirmation
                    String confirmation = scanner.nextLine().trim().toUpperCase(); //accepts lowercase tooand turns to uppercase
                    if (confirmation.equals("Y")) {
                        exit = true;
                        System.out.println("You exited the program.");
                    } else {
                        continueUsing();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 9.");
                    continueUsing();
            }
        }
    }

    /**
     * Method to print all the menu options to the user interface.
     */
    private static void showTheMenu() {
        System.out.println("\nMENU");
        System.out.println("\nPARKING SPOT TASKS");
        System.out.println("1. Create a new parking spot");
        System.out.println("2. Delete a parking spot");
        System.out.println("3. List all spots");
        System.out.println("4. Reset car park");
        System.out.println("\nCAR TASKS");
        System.out.println("5. Park a car");
        System.out.println("6. Find a car by registration number");
        System.out.println("7. Find a car by make");
        System.out.println("8. Remove a car by registration number");
        System.out.println("\n9. EXIT THE PROGRAM");
        System.out.print("\nEnter the number of your choice: ");
    }

    /**
     * Method displays the message to continue using the program and waits for response.
     */
    private static void continueUsing() {
        System.out.print("\nPress any key to continue...");
        scanner.nextLine();
    }

    /**
     * Method to initiate add a parking spot process using the user input:
     * - Gets user input for id
     * - Checks if the format is correct
     * - Checks if spot already existed
     * - If the input is valid and spot does not exist spotId is transferred to addASpot method as a parameter 
     * and spot is added to the car park.
     */
    private static void addParkingSpot() {
        System.out.print("\nEnter a spot ID (e.g., A001): ");
        String spotId = scanner.nextLine(); //accept input

        if (spotId.length() != 4 || !Character.isUpperCase(spotId.charAt(0)) || !Character.isDigit(spotId.charAt(1)) //input validation
                || !Character.isDigit(spotId.charAt(2)) || !Character.isDigit(spotId.charAt(3))) {
            System.out.println("\nINVALID spot ID.");
            System.out.println("ID format must be a capital letter followed by 3 digits.");
            continueUsing();
            return;
        }

        if (carPark.spotExisted(spotId)) { //check if spot already existed 
            System.out.println("\nSPOT ALREADY EXISTS. Please try with a new spot ID.");
            continueUsing();
            return; 
        }

        carPark.addASpot(spotId); //add the spot
        System.out.println("\nParking spot ADDED!");
        continueUsing();
    }

    /**
     * Method to initiate delete parking spot process using the spotId provided by the user
     * - Gets the user input
     * - Checks if the input is valid
     * - Checks if there is such spot
     * - Checks if the spot is occupied
     * - If the input is valid, spot exists and not occupied, spot gets deleted.
     */
    private static void deleteParkingSpot() {
        System.out.print("Enter the spot ID to delete: ");
        String spotId = scanner.nextLine(); //accept input

        if (spotId.length() != 4 || !Character.isUpperCase(spotId.charAt(0)) || !Character.isDigit(spotId.charAt(1)) //input validation
                || !Character.isDigit(spotId.charAt(2)) || !Character.isDigit(spotId.charAt(3))) {
            System.out.println("\nINVALID spot ID.");
            System.out.println("ID format must be a capital letter followed by 3 digits.");
            continueUsing();
            return;
        }

        if (!carPark.spotExisted(spotId)) { //check if spot existed in carpark
            System.out.println("\nSUCH SPOT DOES NOT EXIST."); 
            continueUsing(); 
            return; 
        }

        if (carPark.spotOccupied(spotId)) { //check if spot is occupied or empty
            System.out.println("\nThis spot is OCCUPIED.");
            System.out.println("Occupied spot cannot be deleted. Empty the spot first."); 
            continueUsing(); 
            return;
        }

        carPark.deleteASpot(spotId); //delete the spot
        System.out.println("\nParking spot DELETED!");
        continueUsing();
    }

    /**
     * Method to apply listSpots method to carPark in order to list all the parking spots created
     */
    private static void listAllSpots() {
        carPark.listSpots();
        continueUsing();
    }

    /**
     * Method to initiate park a car process.
     * PARKING SPOT: Gets user input, Checks if the input is valid, Checks if the spot is in the carpark, Checks if the port is occupied or empty
     * REGISTRATION NO: Gets the user input, Checks if the input is valid, Check if the car is already parked
     * MAKE:Gets input from user
     * MODEL:Gets input from user
     * YEAR: Gets input from user, makes sure it's an integer not a string, makes sure the year provided is between 2004 and 2024.
     * If all conditions are met, new car object is created and parked on a spot.
     */
    private static void parkCar() {
        System.out.print("Enter the spot ID to park the car: ");
        String spotId = scanner.nextLine(); //input for spot ID

        if (spotId.length() != 4 || !Character.isUpperCase(spotId.charAt(0)) || !Character.isDigit(spotId.charAt(1)) //Input validation
                || !Character.isDigit(spotId.charAt(2)) || !Character.isDigit(spotId.charAt(3))) {
            System.out.println("\nINVALID spot ID.");
            continueUsing();
            return;
        }

        if (!carPark.spotExisted(spotId)) { //Spot existence check
            System.out.println("\nSPOT IS NOT FOUND in the system.");
            System.out.println("Make sure the spot is added before parking a car.");
            continueUsing();
            return; 
        }

        if (carPark.spotOccupied(spotId)) { //Spot occupation check
            System.out.println("\nSPOT IS OCCUPIED. Please try another spot.");
            continueUsing(); 
            return;
        }

        System.out.print("\nEnter the car registration number (e.g., A1234): "); 
        String registration = scanner.nextLine(); //Input registration number

        if (registration.length() != 5 || !Character.isUpperCase(registration.charAt(0)) || !Character.isDigit(registration.charAt(1)) //input validation
                || !Character.isDigit(registration.charAt(2)) || !Character.isDigit(registration.charAt(3))
                || !Character.isDigit(registration.charAt(4))) {
            System.out.println("\nINVALID registration format.");
            System.out.println("A capital letter must be followed with 4 digits.");
            continueUsing();
            return;
        }

        if (carPark.carExists(registration)) { //checking if the car already exists
            System.out.println("\nCar with registration " + registration + " is already parked. Please try again.");
            continueUsing(); 
            return;
        }

        System.out.print("Enter the car make: ");
        String make = scanner.nextLine(); //car make input

        System.out.print("Enter the car model: ");
        String model = scanner.nextLine(); //model input

        int year; //variable for storing the year
        while (true) { //loops until user gives a valid year
            System.out.print("Enter the car year (between 2004 and 2024): ");
            try {
                year = scanner.nextInt(); 
                scanner.nextLine(); 
                if (year >= 2004 && year <= 2024) { //year validation
                    break; 
                }
                System.out.println("\nINVALID year. Please provide a year from 2004 to 2024.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input from the scanner 
            }
        }

        Car car = new Car(registration, make, model, year); //new object created with given inputs
        carPark.placeCarToSpot(spotId, car); //new object parked on a spot
        System.out.println("\nCar is PARKED!");
        continueUsing();
    }

    /**
     * Method to initiate search car by registration process
     * - Gets input from user
     * - Checks the validation
     * - SearchCarByRegistration method is being called on carPark object and the data is stored in spotId variable
     * - If the value is not null system shows the car in a parking spot
     */
    private static void findCarByRegistrationNo() {
        System.out.print("Enter the car registration number: ");
        String registration = scanner.nextLine(); //input

        if (registration.length() != 5 || !Character.isUpperCase(registration.charAt(0)) || !Character.isDigit(registration.charAt(1)) //validation
                || !Character.isDigit(registration.charAt(2)) || !Character.isDigit(registration.charAt(3))
                || !Character.isDigit(registration.charAt(4))) {
            System.out.println("\nINVALID registration format. Please try again.");
            continueUsing();
            return;
        }

        String spotId = carPark.searchCarByRegistration(registration); //calling the method on an object

        if (spotId == null) { //checking
            System.out.println("\nCar NOT FOUND in the car park."); 
        } else {
            System.out.println("\nCar with registration " + registration + " is parked in spot " + spotId);
        }
        continueUsing();
    }

    /**
     * Method to initiate removing car by registration number process.
     * - Gets input from user
     * - Checking validation
     * - Calling searchCarByRegistration method on carPark object and storing the data in spotId variable
     * - If spotId is not null, deletes the car from the park
     */
    private static void removeCarByRegistrationNo() {
        System.out.print("\nEnter the car registration number: ");
        String registration = scanner.nextLine(); //input

        if (registration.length() != 5 || !Character.isUpperCase(registration.charAt(0)) || !Character.isDigit(registration.charAt(1)) //validation
                || !Character.isDigit(registration.charAt(2)) || !Character.isDigit(registration.charAt(3))
                || !Character.isDigit(registration.charAt(4))) {
            System.out.println("\nINVALID registration format.");
            System.out.println("A capital letter must be followed with 4 digits.");
            continueUsing();
            return;
        }

        String spotId = carPark.searchCarByRegistration(registration); // calling method on the carPark object

        if (spotId == null) { //see if the stored value is null
            System.out.println("\nCAR NOT FOUND in the car park."); 
        } else {
            carPark.removeCarFromSpot(spotId); //remove if there is a stored value
            System.out.println("\nCar with registration " + registration + " removed from spot " + spotId);
        }
        continueUsing();
    }

    /**
     * Method to initiate finding car by make process
     * - Gets input from the user
     * - Passes the input to searchCarByMake method
     * - Calls searchCarByMake method on to carPark object
     */
    private static void findCarsByMake() {
        System.out.print("\nEnter the car make: ");
        String make = scanner.nextLine();
        carPark.searchCarByMake(make);
        continueUsing();
    }

    /**
     * Method to initate reset car park process
     * - Asks for confirmation before deleting the whole cars from the car park
     * - Calls resetCarPark process on to carPark object
     */
    private static void resetCarPark() {
        String confirmationToReset;
        while (true) { //to prevent potential user mistakes
            System.out.print("\nAre you sure you want to reset the car park? (Y/N): "); 
            confirmationToReset = scanner.nextLine().trim().toUpperCase();
            if (confirmationToReset.equals("Y") || confirmationToReset.equals("N")) { 
                break;
            }
            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
        }
    
        if (confirmationToReset.equals("Y")) {
            carPark.resetCarPark();
            System.out.println("\nCar park has been reset. ALL CARS ARE REMOVED from the spots.");
        } else {
            System.out.println("\nCar park reset cancelled.");
        }
        
        continueUsing();
    }
}