import java.util.ArrayList;
import java.util.Scanner;



class Student {
    private String name;
    private double grade;

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }
}

public class StudentGradeTracker {

    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("-------------------------------------------");
        System.out.println("   Welcome to CodeAlpha Grade Tracker   ");
        System.out.println("-------------------------------------------");

        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getIntInput("Select an option: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displaySummary();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting... Thank you for using the Grade Tracker!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n1. Add Student and Grade");
        System.out.println("2. Display Summary Report");
        System.out.println("3. Exit");
    }

    
    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        double grade = -1;
        while (grade < 0 || grade > 100) {
            System.out.print("Enter grade (0-100): ");
            try {
                grade = Double.parseDouble(scanner.nextLine());
                if (grade < 0 || grade > 100) {
                    System.out.println("Error: Grade must be between 0 and 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numerical value for the grade.");
            }
        }

        students.add(new Student(name, grade));
        System.out.println("Student added successfully!");
    }

    
    private static void displaySummary() {
        if (students.isEmpty()) {
            System.out.println("\nNo student data available to display.");
            return;
        }

        double total = 0;
        double highest = students.get(0).getGrade();
        double lowest = students.get(0).getGrade();
        String topStudent = students.get(0).getName();
        String lowStudent = students.get(0).getName();

        System.out.println("\n--- Student Summary Report ---");
        System.out.printf("%-15s | %-5s%n", "Name", "Grade");
        System.out.println("-----------------------------");

        for (Student s : students) {
            double g = s.getGrade();
            total += g;

            if (g > highest) {
                highest = g;
                topStudent = s.getName();
            }
            if (g < lowest) {
                lowest = g;
                lowStudent = s.getName();
            }

            System.out.printf("%-15s | %-5.2f%n", s.getName(), g);
        }

        double average = total / students.size();

        System.out.println("-----------------------------");
        System.out.printf("Total Students: %d%n", students.size());
        System.out.printf("Average Grade:  %.2f%n", average);
        System.out.printf("Highest Grade:  %.2f (Student: %s)%n", highest, topStudent);
        System.out.printf("Lowest Grade:   %.2f (Student: %s)%n", lowest, lowStudent);
        System.out.println("-----------------------------");
    }

    
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}