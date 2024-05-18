package Service;


import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private static List<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void addUser(User user) {
        users.add(user);
    }

    public static void createUser() {
        System.out.println("Adding a new customer...");
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

        users.add(newUser);
        System.out.println("Adding a new user...");
        System.out.println();
    }

    public static void viewAllUsers() {
        System.out.println("All Users:");
        for (User user : users) {
            System.out.println(user.getUserId() + ": " + user.getFirstName() + " " + user.getLastName());
        }
        if (users.isEmpty()) {
            System.out.println("No users found.");
        }
        System.out.println();
    }

    public static void deleteUser() {
        System.out.println("Deleting a user...");
        System.out.print("Enter user ID to delete: ");
        int userIdToDelete = scanner.nextInt();
        scanner.nextLine();

        User userToDelete = null;
        for (User user : users) {
            if (user.getUserId() == userIdToDelete) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete != null) {
            users.remove(userToDelete);
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User with ID " + userIdToDelete + " not found.");
        }
        System.out.println();
    }
}

