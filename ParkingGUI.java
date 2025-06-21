
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * PARKING GUI CLASS: This class contains all the GUI elements, action listeners to create visual communication for the user. 
 * @author Nur Sarikaya <104520751>
 * @file version 1.3 - Java version 21.0.2
 * @date 18/04/2024
 * References: https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/layout/border.html - How to use BorderLayout
 * https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/toplevel.html- Containers
 * https://www.youtube.com/watch?v=zozqhY4c67c&ab_channel=KyleEggleston - pack()
 * https://www.geeksforgeeks.org/stringbuilder-class-in-java-with-examples/ - String Builder
 * 
 */



 //ParkingGUI inherits all the properties and methods of JFrame,implements the actionPerformed(ActionEvent e) method.
public class ParkingGUI extends JFrame implements ActionListener {
    private CarPark carPark; //private instance variable named carPark of type CarPark
    private JTextArea outputArea; // outputArea of type JTextArea
    private List<JButton> parkingSpots; //parkingSpots of type List<JButton>.
    private JPanel spotPanel;//spotPanel of type JPanel
    
    /**
     * Constructor of parking GUI class.
     * initComponents(): initialises the components of GUI
     */
    public ParkingGUI() {
        carPark = new CarPark();
        initComponents();
    }
    
    /**
     * This method contains details to initialise the GUI components and sets up the layout.
     * It also creates the items such as menu, buttons, text areas etc.
     */
    private void initComponents() {
        setTitle("Car Park Management System"); //title of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Program will exit when the window is closed
        setPreferredSize(new Dimension(800, 600)); //size of the JFrame W800 H600

        JPanel contentArea = new JPanel(new BorderLayout()); //new panel to be the main content area, managed by BorderLayout

        JMenuBar menuBar = new JMenuBar(); //JMenuBar named menuBar to hold the menu items.
        JMenu spotMenu = new JMenu("Parking Spot"); //Parking Spot related menu items
        JMenuItem addSpotItem = new JMenuItem("Add Spot");
        addSpotItem.addActionListener(this); //actionPerformed() will be called when clicked
        JMenuItem deleteSpotItem = new JMenuItem("Delete Spot");
        deleteSpotItem.addActionListener(this);
        JMenuItem listSpotsItem = new JMenuItem("List All Spots");
        listSpotsItem.addActionListener(this);
        //Adding menu items to the spotMenu
        spotMenu.add(addSpotItem);
        spotMenu.add(deleteSpotItem);
        spotMenu.add(listSpotsItem);

        JMenu carMenu = new JMenu("Car"); //menu items related to the car 
        JMenuItem parkCarItem = new JMenuItem("Park Car");
        parkCarItem.addActionListener(this);//actionPerformed() will be called when clicked
        JMenuItem findCarByRegItem = new JMenuItem("Find Car by Registration");
        findCarByRegItem.addActionListener(this);
        JMenuItem findCarByMakeItem = new JMenuItem("Find Car by Make");
        findCarByMakeItem.addActionListener(this);
        JMenuItem removeCarItem = new JMenuItem("Remove Car");
        removeCarItem.addActionListener(this);
        //adding menu items to the carMenu
        carMenu.add(parkCarItem);
        carMenu.add(findCarByRegItem);
        carMenu.add(findCarByMakeItem);
        carMenu.add(removeCarItem);

        JMenuItem resetItem = new JMenuItem("Reset Car Park");//Reset car park menu title
        resetItem.addActionListener(this);//actionPerformed() will be called when clicked
        
        //adding menu titles to the menuBar
        menuBar.add(spotMenu);
        menuBar.add(carMenu);
        menuBar.add(resetItem);
        setJMenuBar(menuBar);//sets menu bar as menuBar

        spotPanel = new JPanel(new GridLayout(3, 5, 10, 10));//spot panel with 3 rows and 5 columns, and a horizontal and vertical gap of 10 pixels
        parkingSpots = new ArrayList<>(); //empty list to store parking spot buttons
        contentArea.add(spotPanel, BorderLayout.CENTER); //adds the spotPanel to the contentArea and centres it.

        outputArea = new JTextArea(10, 30); //a text area to show the program log, errors etc. This helps user to track the history.
        outputArea.setEditable(false); //not editable, readonly
        JScrollPane scrollPane = new JScrollPane(outputArea);//this is to store lots of logs and read them through scrolling
        contentArea.add(scrollPane, BorderLayout.SOUTH);

        setContentPane(contentArea);
        pack();//packs the JFrame to make all components properly sized and positioned 
        setLocationRelativeTo(null); //centers the JFrame on the screen 
        setVisible(true); //JFrame visible
    }
    
    /**
     * Handles the action events and does the corresponding operations. This is where we connect the GUI to the methods going on
     * in other classess
     * 
     *  @param e the ActionEvent object for the user's action
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); //retrieves the action command
        switch (command) { //compare the command variable against different case labels
            case "Add Spot": 
                addParkingSpot();
                break;
            case "Delete Spot":
                deleteParkingSpot();
                break;
            case "List All Spots":
                listAllSpots();
                break;
            case "Park Car":
            String spotId = promptToGetSpotId(); 
                if (spotId != null) {
                    parkCarInSpot(spotId);
                }
                break;
            case "Find Car by Registration":
                findCarByRegistrationNo();
                break;
            case "Find Car by Make":
                findCarsByMake();
                break;
            case "Remove Car":
                removeCarByRegistrationNo();
                break;
            case "Reset Car Park":
                resetCarPark();
                break;
            default:
                // variable is used within the actionPerformed method to handle the action events triggered by clicking on a parking spot button
                JButton parkingSpotArea = (JButton) e.getSource(); //default
                if (parkingSpotArea.getBackground().equals(Color.GREEN)) { 
                    parkCarInSpot(parkingSpotArea.getText()); //this is to park a car when the parking spot is green 
                } else if (parkingSpotArea.getBackground().equals(Color.RED)) {
                    String registration = JOptionPane.showInputDialog(this, "Enter the car registration number:");
                    if (registration != null && !registration.isEmpty()) {
                        removeCarByRegistrationNo(registration); //this is to remove car when the parking spot is red
                    }
                }
                break;
        }
    }
    
    /**
     * This method handles to validation side of adding a parking spot,output shown on output area.
     */
    private void addParkingSpot() {
        String spotId = JOptionPane.showInputDialog(this, "Enter a spot ID (e.g., A001):");
        if (spotId != null && !spotId.isEmpty()) {
            if (spotId.length() != 4 || !Character.isUpperCase(spotId.charAt(0)) || !Character.isDigit(spotId.charAt(1))
                    || !Character.isDigit(spotId.charAt(2)) || !Character.isDigit(spotId.charAt(3))) {
                showOutput("INVALID spot ID. ID format must be a capital letter followed by 3 digits."); 
            } else if (carPark.spotExisted(spotId)) {
                showOutput("SPOT ALREADY EXISTS. Please try with a new spot ID.");
            } else {
                carPark.addASpot(spotId); //if valid adds the spot
                showOutput("Parking spot ADDED!");
                JButton parkingSpot = createparkingSpot(spotId); //creates a parking spot
                spotPanel.add(parkingSpot);
                spotPanel.revalidate(); //notify the layout manager that the spotPanel has been modified and needs to be laid out again
                spotPanel.repaint();//updates the visual appearance
            }
        }
    }
    
    /**
     * This method handles the validation related to the delete parking spot actions, output shown on output area.
     */
    private void deleteParkingSpot() {
        String spotId = JOptionPane.showInputDialog(this, "Enter the spot ID to delete:");
        if (spotId != null && !spotId.isEmpty()) {
            if (spotId.length() != 4 || !Character.isUpperCase(spotId.charAt(0)) || !Character.isDigit(spotId.charAt(1))
                    || !Character.isDigit(spotId.charAt(2)) || !Character.isDigit(spotId.charAt(3))) {
                showOutput("INVALID spot ID. ID format must be a capital letter followed by 3 digits.");
            } else if (!carPark.spotExisted(spotId)) {
                showOutput("SUCH SPOT DOES NOT EXIST.");
            } else if (carPark.spotOccupied(spotId)) {
                showOutput("This spot is OCCUPIED. Occupied spot cannot be deleted. Empty the spot first.");
            } else {
                carPark.deleteASpot(spotId); //if valid 
                showOutput("Parking spot DELETED!"); 
                removeparkingSpot(spotId);//deletes the parking spot
            }
        }
    }
    
    /**
     * This method shows the carPark.listSpots() output on the outputarea
     */
    private void listAllSpots() {
        StringBuilder listArea = new StringBuilder(); //string builder
        listArea.append("List of all parking spots:\n");
    
        int allSpots = carPark.getParkingSpots().size();
        int occupiedSpots = 0;
        for (ParkingSpot spot : carPark.getParkingSpots()) {
            listArea.append(spot.toString()).append("\n");
            if (spot.spotTaken()) {
                occupiedSpots++;
            }
        }
    
        int emptySpots = allSpots - occupiedSpots;
    
        listArea.append("\nSummary:\n"); 
        listArea.append("All spots: ").append(allSpots).append("\n");
        listArea.append("Occupied spots: ").append(occupiedSpots).append("\n");
        listArea.append("Empty spots: ").append(emptySpots).append("\n");
    
        showOutput(listArea.toString()); //display
    }
    
    /**
     * To format the date and time 
     * 
     * @param dateTime current date and time
     */
    private String formatDateTime(LocalDateTime dateTime) { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
    
    /**
     * This method prompts the user to enter a spot ID and calls the parkCar(String) method to park the car.
     */
    private String promptToGetSpotId() {
        String spotId = JOptionPane.showInputDialog(this, "Enter the spot ID to park the car:");
    
        if (spotId != null && !spotId.isEmpty()) {
            return spotId;
        }
        return null;
    }   
    
    /**
     * This method checks if all provided information is valid and calls placeCarToSpot() and then makes the parking spot red.
     * 
     * @param spotId ID of an existing parking spot
     */
    private void parkCarInSpot(String spotId) {
        if (spotId.length() != 4 || !Character.isUpperCase(spotId.charAt(0)) || !Character.isDigit(spotId.charAt(1))
                || !Character.isDigit(spotId.charAt(2)) || !Character.isDigit(spotId.charAt(3))) {
            showOutput("INVALID spot ID.");
        } else if (!carPark.spotExisted(spotId)) {
            showOutput("SPOT IS NOT FOUND in the system. Make sure the spot is added before parking a car.");
        } else if (carPark.spotOccupied(spotId)) {
            showOutput("SPOT IS OCCUPIED. Please try another spot.");
        } else {
            String registration = JOptionPane.showInputDialog(this, "Enter the car registration number (e.g., A1234):");

            if (registration != null && !registration.isEmpty()) {
                if (registration.length() != 5 || !Character.isUpperCase(registration.charAt(0))
                        || !Character.isDigit(registration.charAt(1)) || !Character.isDigit(registration.charAt(2))
                        || !Character.isDigit(registration.charAt(3)) || !Character.isDigit(registration.charAt(4))) {
                    showOutput("INVALID registration format. A capital letter must be followed with 4 digits.");
                } else if (carPark.carExists(registration)) {
                    showOutput("Car with registration " + registration + " is already parked. Please try again.");
                } else {
                    String make = JOptionPane.showInputDialog(this, "Enter the car make:");
                    String model = JOptionPane.showInputDialog(this, "Enter the car model:");
                    String yearString = JOptionPane.showInputDialog(this, "Enter the car year (between 2004 and 2024):");

                    if (make != null && !make.isEmpty() && model != null && !model.isEmpty() && yearString != null && !yearString.isEmpty()) {
                        try {
                            int year = Integer.parseInt(yearString);

                            if (year >= 2004 && year <= 2024) {
                                Car car = new Car(registration, make, model, year);
                                carPark.placeCarToSpot(spotId, car); // calling the model, everything is valid
                                showOutput("Car is PARKED!");

                                LocalDateTime parkedTime = car.getParkedTime();
                                String formattedParkedTime = formatDateTime(parkedTime);
                                showOutput("Parking time: " + formattedParkedTime);

                                JButton parkingSpot = findparkingSpot(spotId);
                                if (parkingSpot != null) {
                                    parkingSpot.setBackground(Color.RED);
                                }
                            } else {
                                showOutput("INVALID year. Please provide a year from 2004 to 2024.");
                            }
                        } catch (NumberFormatException ex) {
                            showOutput("Invalid input. Please enter a valid number.");
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Prompts the user to enter a car registration number and searches for the car in the car park.
     * Displays the spot ID of the place of the car if found, or a message if the car is not found.
     */
    private void findCarByRegistrationNo() {
        String registration = JOptionPane.showInputDialog(this, "Enter the car registration number:");
        if (registration != null && !registration.isEmpty()) {
            if (registration.length() != 5 || !Character.isUpperCase(registration.charAt(0))
                    || !Character.isDigit(registration.charAt(1)) || !Character.isDigit(registration.charAt(2))
                    || !Character.isDigit(registration.charAt(3)) || !Character.isDigit(registration.charAt(4))) {
                showOutput("INVALID registration format. Please try again.");
            } else {
                String spotId = carPark.searchCarByRegistration(registration);
                if (spotId == null) {
                    showOutput("Car NOT FOUND in the car park.");
                } else {
                    showOutput("Car with registration " + registration + " is parked in spot " + spotId);
                }
            }
        }
    }
    
    /**
     * Prompts the user to enter a car make and searches for cars with the specified make in the car park.
     * Displays the list of cars found with the specified make.
     */
    private void findCarsByMake() {
        String make = JOptionPane.showInputDialog(this, "Enter the car make:");
    
        if (make != null && !make.isEmpty()) {
            StringBuilder listArea = new StringBuilder();
            listArea.append("Cars with make ").append(make).append(" parked in the car park:\n");
    
            boolean carFound = false;
            for (ParkingSpot spot : carPark.getParkingSpots()) {
                if (spot.spotTaken() && spot.getParkedCar().getCarMake().equalsIgnoreCase(make)) {
                    listArea.append(spot.toString()).append("\n");
                    carFound = true;
                }
            }
    
            if (!carFound) {
                listArea.append("There is no car with ").append(make).append(" make in the car park.");
            }
    
            showOutput(listArea.toString());
        }
    }

    /** 
     * Prompts the user to enter a car registration number and calls the removeCarByRegistrationNo(String) 
     * to remove the car from the car park.
     */
    private void removeCarByRegistrationNo() {
        String registration = JOptionPane.showInputDialog(this, "Enter the car registration number:");
        if (registration != null && !registration.isEmpty()) {
            removeCarByRegistrationNo(registration);
        }
    }

    /**
     * Removes a car from the car park using registration number.
     *
     * @param registration The registration number of the car.
     */
    private void removeCarByRegistrationNo(String registration) {
        if (registration.length() != 5 || !Character.isUpperCase(registration.charAt(0))
                || !Character.isDigit(registration.charAt(1)) || !Character.isDigit(registration.charAt(2))
                || !Character.isDigit(registration.charAt(3)) || !Character.isDigit(registration.charAt(4))) {
            showOutput("INVALID registration format. A capital letter must be followed with 4 digits."); //show on output area
        } else {
            String spotId = carPark.searchCarByRegistration(registration);
            if (spotId == null) {
                showOutput("CAR NOT FOUND in the car park."); 
            } else {
                carPark.removeCarFromSpot(spotId);
                showOutput("Car with registration " + registration + " removed from spot " + spotId);
                JButton parkingSpot = findparkingSpot(spotId); //find the spot to make it green
                if (parkingSpot != null) {
                    parkingSpot.setBackground(Color.GREEN); //spot becomes green after the removal
                }
            }
        }
    }
    
    /**
     * Resets the car park by removing all cars and changed the GUI.
     * Prompts the user for confirmation before reset.
     */
    private void resetCarPark() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the car park?", 
                                                    "Confirmation", JOptionPane.YES_NO_OPTION); //Option Pane
        if (confirm == JOptionPane.YES_OPTION) {
            carPark.resetCarPark();
            showOutput("Car park has been reset. ALL CARS ARE REMOVED from the spots.");
            resetparkingSpots();
        }
    }   
    
    /**
     * Displays the given message in the output area of the GUI.
     *
     * @param message The message to be displayed.
     */
    private void showOutput(String message) { 
        outputArea.append(message + "\n");
    }
    
    /**
     * Creates a new JButton representing a parking spot with the given spot ID.
     *
     * @param spotId The ID of the parking spot.
     * @return The created JButton instance.
     */
    private JButton createparkingSpot(String spotId) {
        JButton parkingSpot = new JButton(spotId); //the physical parking spot in the GUI
        parkingSpot.setBackground(Color.GREEN); // create a green one at first
        parkingSpot.addActionListener(this); //add action listener to it
        parkingSpots.add(parkingSpot); //add to the lists of parking spots
        return parkingSpot;
    }

    /**
     * Removes the JButton of the parking spot with the given spot ID.
     *
     * @param spotId The ID of the parking spot.
     */
    private void removeparkingSpot(String spotId) { 
        for (JButton parkingSpot : parkingSpots) {
            if (parkingSpot.getText().equals(spotId)) { 
                parkingSpots.remove(parkingSpot);//removes it from the list
                spotPanel.remove(parkingSpot);//removes it from GUI
                spotPanel.revalidate();
                spotPanel.repaint();
                return;
            }
        }
    }

    /**
     * Resets the color of all spot buttons to green.
     */
    private void resetparkingSpots() {
        for (JButton parkingSpot : parkingSpots) {
            parkingSpot.setBackground(Color.GREEN);
        }
    }
    
    /**
     * Finds the JButton for parking spot with the spot ID.
     *
     * @param spotId The ID of the parking spot.
     * @return The JButton parking spot, or null if not found.
     */
    private JButton findparkingSpot(String spotId) {
        for (JButton parkingSpot : parkingSpots) {
            if (parkingSpot.getText().equals(spotId)) {
                return parkingSpot;
            }
        }
        return null;
    }

    /**
     * The main method that launches the parking GUI application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ParkingGUI();
            }
        });
    }
}