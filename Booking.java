public class Booking {
   
    private String passengerName;
    private String date;
    private String time;
    private String pickup;
    private String dropoff;
    private float distance;
    private float fare;
    private boolean isDeleted = false;

    public Booking(String passengerName, String date, String time, String pickup, String dropoff, float distance) {
        this.passengerName = passengerName;
        this.date = date;
        this.time = time;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.distance = distance;
        this.fare = calculateFare();
    }


    private float calculateFare() {
        if (distance <= 1) {
            return 25;
        } else {
            return 25 + 20 * (distance - 1);
        }
    }

    public String getPassengerName() { return passengerName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getPickup() { return pickup; }
    public String getDropoff() { return dropoff; }
    public float getDistance() { return distance; }
    public float getFare() { return fare; }
    public boolean isDeleted() { return isDeleted; }

    public void setDeleted(boolean deleted) { this.isDeleted = deleted; }

    public void displayDetails() {
        if (isDeleted) {
            System.out.println("[Deleted Booking]");
        }
        System.out.println("Passenger: " + passengerName);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("From: " + pickup);
        System.out.println("To: " + dropoff);
        System.out.println("Distance: " + distance + " km");
        System.out.printf("Fare: ₱%.2f%n", fare);
    }

    public String toFileString() {
        return passengerName + "," + date + "," + time + "," + pickup + "," + dropoff + "," +
               distance + "," + fare + "," + isDeleted;
    }

    public static Booking fromFileString(String line) {
        String[] parts = line.split(",");
        Booking b = new Booking(
            parts[0],             
            parts[1],             
            parts[2],             
            parts[3],            
            parts[4],             
            Float.parseFloat(parts[5])
        );
        b.fare = Float.parseFloat(parts[6]);
        b.isDeleted = Boolean.parseBoolean(parts[7]);
        return b;
    }
}
