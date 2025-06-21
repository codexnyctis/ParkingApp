/**
 * CAR CLASS: This class has the information to construct a car object. 
 * Methods: getter methods for each instance variable, setter method for car parking time, toString method 
 * to return the information of car object as a string.
 * @author Nur Sarikaya <104520751>
 * @file version 1.2 - Java version 21.0.2
 * @date 14/04/2024 (due date)
 * References: https://www.javatpoint.com/java-get-current-date (How to show time in Java)
 */

import java.time.LocalDateTime;  //library to return current date and time

/**
 * Car class contains private variables (encapsulation/abstraction) required to contruct a car: 
 * Car registration number, car carModel, car carMake, car year, the time car has parked
 */
class Car { 
    private String registrationNo;
    private String carMake;
    private String carModel;
    private int year;
    private LocalDateTime parkedTime;

    /**
     * Constructor method construct a car object using required variables as parameters.
     * 
     * @param registrationNo the registration number of car
     * @param carMake         car make (brand)
     * @param carModel        car model
     * @param year         production year of the car
     * @param parkedTime   time of the car parked on a spot
     */
    public Car(String registrationNo, String carMake, String carModel, int year) {
        this.registrationNo = registrationNo;   //this keyword targets the current instance of the class
        this.carMake = carMake;
        this.carModel = carModel;
        this.year = year;
        this.parkedTime = null;
    }
    
    /**
     * Gets and returns the registration number
     * 
     * @return registration number (String)
     */
    public String getRegistrationNo() {
        return registrationNo;
    }
    
    /**
     * Gets and returns the car carMake/brand
     * 
     * @return car make/brand (String)
     */
    public String getCarMake() {
        return carMake;
    }

    /**
     * Gets and returns the car carModel
     * 
     * @return car model (String)
     */
    public String getCarModel() {
        return carModel;
    }
    
    /**
     * Gets and returns the car production year
     * 
     * @return production year of car (int)
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the time car parked on a spot using in built Java class: LocalDateTime
     * 
     * @return the time that a car parked on a spot (LocalDateTime)
     */
    public LocalDateTime getParkedTime() {
        return parkedTime;
    }

    /**
     * Sets the time of parking information 
     * 
     * @param parkedTime time of parking
     */
    public void setParkedTime(LocalDateTime parkedTime) {  //void does not return anything, only sets the output of the get method
        this.parkedTime = parkedTime;
    }

    /**
     * Returns the car object information as a string
     * 
     * @return car object information as a string
     */
    public String toString() {
        String parkedTimeString;
        if (parkedTime != null) {
            parkedTimeString = parkedTime.toString();
        } else {
            parkedTimeString = "Not parked";
        }
        
        return "Car [registrationNo=" + registrationNo + ", make=" + carMake + ", model=" + carModel + ", year=" + year +
                ", parkedTime=" + parkedTimeString + "]";
    }
}