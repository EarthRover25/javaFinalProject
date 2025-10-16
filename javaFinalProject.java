import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class javaFinalProject {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<Booking> bookings = new ArrayList<>();
    public static void main(String[] args){
        loadBookingsFromFile();

        char choice;
        
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
            if (input.isEmpty()) {
                System.out.println("\n Invalid input! Enter a letter from a-e.");
                continue;
            }

            choice = input.charAt(0);
            if (choice < 'a' || choice > 'e') {
                System.out.println("\n Invalid input! Enter a letter from a-e.");
                continue;
            }

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
                    System.exit(0);
            }
        }    
    }

    public static void viewBooking() {
        System.out.println("\n VIEW ALL BOOKINGS");
        boolean anyAvtive = false;

        if (bookings.isEmpty()) {
            System.out.println("\n No bookings found.");
            return;
        }

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
    
    public static void bookRide() {
        System.out.println("\n BOOK A RIDE");

        System.out.print("Enter passenger name: ");
        String passengerName = scan.nextLine();

        System.out.print("Enter the date: ");
        String date = scan.nextLine();
    
        System.out.print("Enter the time: ");
        String time = scan.nextLine();

        System.out.print("Enter pickup location: ");
        String pickup = scan.nextLine();

        System.out.print("Enter destination: ");
        String dropoff = scan.nextLine();

        System.out.print("Enter distance: ");
        float distance = scan.nextFloat();
        
        scan.nextLine();
    

        Booking newBooking = new Booking(passengerName, date, time, pickup, dropoff, distance);
        bookings.add(newBooking);

        System.out.println("\nBooking added successfully!");
    }

    public static void delBooking() {
        System.out.println("\n DELETE BOOKING");

        if (bookings.isEmpty()) {
            System.out.println("No bookings available to delete.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-10s %-15s %-15s %-10s %-10s%n",
        "No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare");
    
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            System.out.printf("%-5d %-20s %-10s %-10s %-15s %-15s %-10.2f %-10.2f%n",
                (i + 1),
                b.getPassengerName(),
                b.getDate(),
                b.getTime(),
                b.getPickup(),
                b.getDropoff(),
                b.getDistance(),
                b.getFare());
        }

        System.out.print("Enter the booking number to delete: ");
        try {
            int index = Integer.parseInt(scan.nextLine());

            if (index < 1 || index > bookings.size()) {
                System.out.println("Invalid booking number!");
            } 
            else {
                Booking b = bookings.get(index - 1);
                b.setDeleted(true); 
                System.out.println("\nBooking for " + b.getPassengerName() + " has been deleted.");
            }
        } 
        catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
    public static void genBookReport() {
        System.out.println("\n BOOKING REPORT");

        if (bookings.isEmpty()) {
        System.out.println("No bookings to report.");
        return;
        }

        System.out.printf("%-5s %-20s %-10s %-10s %-15s %-15s %-10s %-10s%n",
            "No.", "Passenger", "Date", "Time", "Pickup", "Dropoff", "Distance", "Fare");

        float totalFare = 0;
        float totalDistance = 0;

        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            String deletedMark = b.isDeleted() ? "Deleted" : "Active";
            
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

            totalFare += b.getFare();
            totalDistance += b.getDistance();
        }

        System.out.println("Total Bookings: " + bookings.size());
        System.out.printf("Total Distance: %.2f km%n", totalDistance);
        System.out.printf("Total Fare Collected: %.2f%n", totalFare);
    }
    
    public static void saveBookingsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("bookings.txt"))) {
            for (Booking b : bookings) {
                writer.println(b.toFileString());
            }
        } 
        catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }
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
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }
}
