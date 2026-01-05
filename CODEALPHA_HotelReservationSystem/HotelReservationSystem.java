import java.io.*;
import java.util.*;

/**
 * Task 4: Hotel Reservation System for CodeAlpha Internship.
 * Features: Room categorization, search, booking, payment simulation, 
 * cancellation, and File I/O persistence.
 */

class Room implements Serializable {
    private int roomNumber;
    private String category; // Standard, Deluxe, Suite
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return String.format("Room %d [%-10s] - $%.2f - %s", 
            roomNumber, category, price, (isAvailable ? "Available" : "Occupied"));
    }
}

class Reservation implements Serializable {
    private String guestName;
    private int roomNumber;
    private double amountPaid;

    public Reservation(String guestName, int roomNumber, double amountPaid) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.amountPaid = amountPaid;
    }

    public String getGuestName() { return guestName; }
    public int getRoomNumber() { return roomNumber; }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomNumber + " | Paid: $" + amountPaid;
    }
}

public class HotelReservationSystem {
    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static final String DATA_FILE = "hotel_data.dat";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();
        if (rooms.isEmpty()) initializeRooms();

        while (true) {
            System.out.println("\n--- CodeAlpha Hotel Management System ---");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View My Booking Details");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": searchRooms(); break;
                case "2": makeReservation(); break;
                case "3": viewBookings(); break;
                case "4": cancelReservation(); break;
                case "5": 
                    saveData();
                    System.out.println("Data saved. Goodbye!");
                    return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 50.0));
        rooms.add(new Room(102, "Standard", 50.0));
        rooms.add(new Room(201, "Deluxe", 100.0));
        rooms.add(new Room(202, "Deluxe", 100.0));
        rooms.add(new Room(301, "Suite", 250.0));
    }

    private static void searchRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (r.isAvailable()) System.out.println(r);
        }
    }

    private static void makeReservation() {
        searchRooms();
        System.out.print("\nEnter Room Number to book: ");
        int roomNum = Integer.parseInt(scanner.nextLine());

        Room selectedRoom = null;
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNum && r.isAvailable()) {
                selectedRoom = r;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not available or doesn't exist.");
            return;
        }

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Payment Simulation
        System.out.println("Processing payment of $" + selectedRoom.getPrice() + "...");
        System.out.print("Enter dummy Credit Card Number: ");
        scanner.nextLine(); // simulate input
        
        selectedRoom.setAvailable(false);
        reservations.add(new Reservation(name, roomNum, selectedRoom.getPrice()));
        System.out.println("Booking Successful!");
    }

    private static void viewBookings() {
        if (reservations.isEmpty()) {
            System.out.println("No active reservations.");
            return;
        }
        for (Reservation res : reservations) {
            System.out.println(res);
        }
    }

    private static void cancelReservation() {
        System.out.print("Enter your name to cancel booking: ");
        String name = scanner.nextLine();
        Reservation found = null;

        for (Reservation res : reservations) {
            if (res.getGuestName().equalsIgnoreCase(name)) {
                found = res;
                break;
            }
        }

        if (found != null) {
            for (Room r : rooms) {
                if (r.getRoomNumber() == found.getRoomNumber()) {
                    r.setAvailable(true);
                    break;
                }
            }
            reservations.remove(found);
            System.out.println("Reservation cancelled successfully.");
        } else {
            System.out.println("Reservation not found.");
        }
    }

    // File I/O for Persistence
    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(rooms);
            oos.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            rooms = (List<Room>) ois.readObject();
            reservations = (List<Reservation>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Starting with fresh data.");
        }
    }
}