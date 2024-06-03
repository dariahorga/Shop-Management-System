package Service;

import Models.*;

import Repository.UserRepository;

import java.util.Scanner;

public class UserService {
    public static final Scanner scanner = new Scanner(System.in);

    public static void createUser() {
        System.out.println("Adding a new user...");
        System.out.println();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter phone number: ");
        long phoneNumber = scanner.nextLong();

        User newUser = new Customer(firstName, lastName, email, address, phoneNumber);

        UserRepository.addUser(newUser);
        System.out.println("User added successfully!");
        AuditService.logAction("User added: " + newUser.getFirstName() + " "+ newUser.getLastName());

        System.out.println();
    }

    public static void viewAllUsers() {
        System.out.println("All Users:");
        UserRepository.displayAllUsers();
    }

    public static void deleteUser() {
        System.out.println("Deleting a user...");
        System.out.print("Enter user ID to delete: ");
        int userIdToDelete = scanner.nextInt();
        scanner.nextLine();

        UserRepository.deleteUser(userIdToDelete);
        AuditService.logAction("User with ID " + userIdToDelete+" deleted");
        System.out.println();
    }
}
