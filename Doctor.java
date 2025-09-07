package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner scanner;

    public Doctor(Connection connection,Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add a new doctor
    public void addDoctor() {
        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Doctor Specialization: ");
        String specialization = scanner.nextLine();

        String query = "INSERT INTO doctors(name, specialization) VALUES(?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Doctor Added Successfully!");
            } else {
                System.out.println("❌ Failed to Add Doctor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all doctors
    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");

                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if doctor exists by ID
    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
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
