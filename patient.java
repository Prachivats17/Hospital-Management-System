package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add a new patient
    public void addPatient() {
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender (Male/Female/Other): ");
        String gender = scanner.next();
        System.out.print("Enter Patient Address: ");
        scanner.nextLine(); // consume leftover newline
        String address = scanner.nextLine();

        String query = "INSERT INTO patients(name, age, gender, address) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, address);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient Added Successfully!");
            } else {
                System.out.println("Failed to Add Patient!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all patients
    public void viewPatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+-----+----------+--------------------+");
            System.out.println("| Patient Id | Name               | Age | Gender   | Address            |");
            System.out.println("+------------+--------------------+-----+----------+--------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String address = resultSet.getString("address");
                System.out.printf("| %-10s | %-18s | %-3d | %-8s | %-18s |\n",
                        id, name, age, gender, address);
                System.out.println("+------------+--------------------+-----+----------+--------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if patient exists by ID
    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
