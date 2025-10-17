import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class javaFinalProject {
    static Scanner scan = new Scanner(System.in); // static scanner for global usage
    static ArrayList<Booking> bookings = new ArrayList<>(); // create static array list
    public static void main(String[] args){
        loadBookingsFromFile();

        char choice;

        // system menu
        while (true){
            System.out.println("\n    RIDE-HAILING BOOKING SYSTEM");
            System.out.println("System Menu");
            System.out.println("a. View All Bookings");
            System.out.println("b. Book a Ride");
            System.out.println("c. Delete Booking");
            System.out.println("d. Generate Booking Report");
            System.out.println("e. Exit Application");
            System.out.print("Enter your choice: ");
            
            String input = scan.nextLine().toLowerCase();

            // check if input is empty
            if (input.isEmpty()) {
                System.out.println("\n Invalid input! Enter a letter from a-e.");
                continue; // restart 
            }

            choice = input.charAt(0);

            // error handling to check if choice is valid
            if (choice < 'a' || choice > 'e') {
                System.out.println("\n Invalid input! Enter a letter from a-e.");
                continue;
            }

            // validation of choice
            switch(choice) {
                case 'a': viewBooking();
                    break;      
                case 'b': bookRide();
                    break;
                case 'c': delBooking();
                    break;
                case 'd': genBookReport();
                    break;
                case 'e':
                    System.out.println("\n THANK YOU! HAVE A SAFE RIDE!");
                    System.exit(0) // exit program
            }
        }    
    }

    // method for view all booking
    public static void viewBooking() {
        System.out.println("\n VIEW ALL BOOKINGS");
        boolean anyAvtive = false;

        if (bookings.isEmpty()) {
            System.out.println("\n No bookings found.");
            return; // return to menu
        }

        // print table format
        System.out.printf("%-5s %-20s %-10s %-10s %-15s %-15s %-10s %-10s%n",
            "No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare");

    
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            if (b.isDeleted()) continue;

        System.out.printf("%-5d %-20s %-10s %-10s %-15s %-15s %-10.2f %-10.2f%n",
                (i + 1),
                b.getPassengerName(),
                b.getDate(),
                b.getTime(),
                b.getPickup(),
                b.getDropoff(),
                b.getDistance(),
                b.getFare());
            
            anyAvtive = true;
        }

        if (!anyAvtive) System.out.println("\n No active bookings found.");
    }

    // method for booking a ride
    public static void bookRide() {
        System.out.println("\n BOOK A RIDE");

        // asks for passenger name
        System.out.print("Enter passenger name: ");
        String passengerName = scan.nextLine();

        // asks for booking date
        System.out.print("Enter the date: ");
        String date = scan.nextLine();

        // asks for pick up time
        System.out.print("Enter the time: ");
        String time = scan.nextLine();

        // asks for location
        System.out.print("Enter pickup location: ");
        String pickup = scan.nextLine();

        // enter the detination
        System.out.print("Enter destination: ");
        String dropoff = scan.nextLine();

        // enter distance in kilometers
        System.out.print("Enter distance (in kilometers): ");
        float distance = scan.nextFloat();
        scan.nextLine(); // debuffer line
    
        // create a new sub array
        Booking newBooking = new Booking(passengerName, date, time, pickup, dropoff, distance);
        bookings.add(newBooking); // save user input into database

        System.out.println("\nBooking added successfully!");
    }

    // method for deleting bookingssss
    public static void delBooking() {
        System.out.println("\n DELETE BOOKING");

        // check if database contains any active booking
        if (bookings.isEmpty()) {
            System.out.println("No bookings available to delete.");
            return; // return to menu
        }

        // print format table
        System.out.printf("%-5s %-20s %-10s %-10s %-15s %-15s %-10s %-10s%n",
        "No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare");
    
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            System.out.printf("%-5d %-20s %-10s %-10s %-15s %-15s %-10.2f %-10.2f%n",
                (i + 1),

                // get object
                b.getPassengerName(),
                b.getDate(),
                b.getTime(),
                b.getPickup(),
                b.getDropoff(),
                b.getDistance(),
                b.getFare());
        }

        // user input for deleting a booking
        System.out.print("Enter the booking number to delete: ");
        try {
            int index = Integer.parseInt(scan.nextLine()); // converts string to int

            // condition to check if booking number is in database
            if (index < 1 || index > bookings.size()) {
                System.out.println("Invalid booking number!");
            } 

                // call object to validate booking deletion
            else {
                Booking b = bookings.get(index - 1);
                b.setDeleted(true); 
                System.out.println("\nBooking for " + b.getPassengerName() + " has been deleted.");
            }
        } 
            // error handling for invalid input
        catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // method for generating booking report
    public static void genBookReport() {
        System.out.println("\n BOOKING REPORT");

        // check databse if booking has a value
        if (bookings.isEmpty()) {
        System.out.println("No bookings to report.");
        return;
        }

        // format table 
        System.out.printf("%-5s %-20s %-10s %-10s %-15s %-15s %-10s %-10s%n",
            "No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare");

        float totalFare = 0;
        float totalDistance = 0;

        // format generate booking report table
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            String deletedMark = b.isDeleted() ? "Deleted" : "Active";

            // call object to program the format
            System.out.printf("%-5d %-20s %-10s %-10s %-15s %-15s %-10.2f %-10.2f %-10s%n",
                (i + 1),
                b.getPassengerName(),
                b.getDate(),
                b.getTime(),
                b.getPickup(),
                b.getDropoff(),
                b.getDistance(),
                b.getFare(),
                deletedMark);

            totalFare += b.getFare(); // add the overall sum of all fee
            totalDistance += b.getDistance(); // add the overall distance created
        }

        System.out.println("Total Bookings: " + bookings.size());
        System.out.printf("Total Distance: %.2f km%n", totalDistance);
        System.out.printf("Total Fare Collected: %.2f%n", totalFare);
    }

    // method for saving value in database
    public static void saveBookingsToFile() {

        // used printwriter to edit what type of file to edit
        try (PrintWriter writer = new PrintWriter(new FileWriter("bookings.txt"))) {

            // loop inside the array list
            for (Booking b : bookings) {
                writer.println(b.toFileString()); // writes inside the booking.txt file
            }
        } 
        catch (IOException e) {
            System.out.println("Error bookings: " + e.getMessage()); // debugging
        }
    }

    // import database to system
    public static void loadBookingsFromFile() {
        bookings.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Booking b = Booking.fromFileString(line);
                bookings.add(b);
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("No saved bookings found, starting fresh.");
        } 
        catch (IOException e) {
            System.out.println("Error bookings: " + e.getMessage()); // debugging
        }
    }
}
