import java.util.*;

public class LoginSystem {
    private Map<String, User> users;   // Map to store user accounts by username
    private Map<String, Admin> adminUsers; // Correctly declare adminUsers

    private Scanner scanner;
    private Menu menu; // Shared Menu instance

    public LoginSystem() {
        this.users = new HashMap<>();
        this.adminUsers = new HashMap<>(); // Initialize adminUsers
        this.scanner = new Scanner(System.in);
        this.menu = new Menu(); // Initialize Menu instance

        // Add sample users and admin
        Admin admin = new Admin("admin1", "password123", menu); // Initialize admin
        users.put("Saurav", new Customer("Saurav", "123", true, menu, admin));
        adminUsers.put("admin1", admin); // Add admin to adminUsers map
    }

    public void startLoginProcess() {
        while (true) {
            System.out.println("\nWelcome to Byte Me! College Canteen Ordering System");
            System.out.println("1. Login\n2. Sign Up (User Only)\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> loginUserOrAdmin();
                case 2 -> signUpUser();
                case 3 -> {
                    System.out.println("Exit to Main Menu");
                    return; // Exit
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handles login selection between User and Admin
    private void loginUserOrAdmin() {
        while (true) {
            System.out.println("\nLogin as:\n1. User\n2. Admin\n3. Exit to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> loginUser();
                case 2 -> loginAdmin();
                case 3 -> {
                    System.out.println("Returning to Main Menu...");
                    return; // Exit to main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Login method for users (students)
    private void loginUser() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

//        User userdata = UserManager.getUserByUsername(username);

        try {
            User user = users.get(username);
            if (user == null || !user.getPassword().equals(password)) {
                throw new AuthenticationException("Invalid username or password.");
            }
            System.out.println("Login successful! Welcome, " + user.getUsername() + ".");
            Customer customer = (Customer) user; // Ensure type casting is correct
            customer.customerInterface(); // Display menu and handle customer interface

        } catch (AuthenticationException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Error: You have attempted to log in as a User with Admin credentials.");
        }
    }

    private void loginAdmin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        try {

            Admin user = adminUsers.get(username);

            if (user == null) {
                throw new AuthenticationException("Username not found.");
            }

            if (!user.getPassword().equals(password)) {
                throw new AuthenticationException("Incorrect password.");
            }

            System.out.println("Admin login successful! Welcome, Admin " + user.getUsername() + ".");
            user.adminInterface();

        } catch (AuthenticationException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void signUpUser() {
        System.out.print("Choose a Username: ");
        String username = scanner.nextLine();
        System.out.print("Create a Password: ");
        String password = scanner.nextLine();

        try {
            if (users.containsKey(username)) {
                throw new RegistrationException("Username already exists. Please choose another.");
            }
            Admin admin = new Admin("admin1", "password123", menu);
            User newUser = new Customer(username, password, false, menu, admin);
            users.put(username, newUser);
            System.out.println("User account created successfully. You can now log in as " + username + ".");

        } catch (RegistrationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
