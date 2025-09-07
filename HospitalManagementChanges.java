HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String USER = "root";
    private static final String PASSWORD = "Prachi@1";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("✅ Connected to Database!");

            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection,scanner);

            while (true) {
                System.out.println("\n--- Hospital Management System ---");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. Add Doctor");
                System.out.println("4. View Doctors");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("⚠️ Please enter a number!");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> patient.addPatient();
                    case 2 -> patient.viewPatients();
                    case 3 -> doctor.addDoctor();
                    case 4 -> doctor.viewDoctors();
                    case 5 -> {
                        System.out.println("👋 Exiting...");
                        return;
                    }
                    default -> System.out.println("⚠️ Invalid choice.");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }
}
