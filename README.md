# 🚗 Car Parking Management System

A Java-based GUI application for managing a car parking lot. This system simulates vehicle entry and exit using a simple graphical interface.

## 📦 Features

* Add and remove cars from parking spots
* GUI interface using Java Swing
* Displays real-time parking status
* Each spot shows its occupancy status

## 🛠️ Technologies Used

* Java 17+
* Java Swing (GUI)
* OOP (Object-Oriented Programming)

## 📁 Project Structure

```
├── Application.java       # Optional entry point (not used to run the GUI)
├── Car.java               # Car model with license plate and brand
├── CarPark.java           # Manages all parking spots and car logic
├── ParkingGUI.java        # GUI for managing the parking interface
├── ParkingSpot.java       # Individual spot and car tracking
```

## 🚀 Getting Started

### Prerequisites

* Java JDK 17 or later
* Terminal or IDE (e.g. IntelliJ, Eclipse)

### Run Instructions

1. **Compile all Java files:**

   ```bash
   javac *.java
   ```

2. **Run the application GUI:**

   ```bash
   java ParkingGUI
   ```

   This launches the graphical interface.

## 🧪 Example Usage

* Click a parking spot to assign a car
* Enter car brand and license plate
* Click an occupied spot to remove the car
* Visual feedback shows available and taken spots
