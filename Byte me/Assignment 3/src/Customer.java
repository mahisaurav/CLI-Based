import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

class Customer extends User implements CustomerType{
    private List<MenuItem> menuItems;
    private Menu menu;
    private List<CartItem> cart = new ArrayList<>();
    private List<Order> orders = new ArrayList<>(); // List to hold orders
    private List<Order> orderList = new ArrayList<>();
    private Map<MenuItem, List<String>> itemReviews = new HashMap<>();
    private boolean vipStatus;
    private static final double VIP_COST = 100.0;
    private Admin admin;


    public Customer(String username, String password, boolean isVIP, Menu menu ,Admin admin) {
        super(username, password);
        this.vipStatus = isVIP;
        this.menu = menu ;
        this.menuItems = menu.getMenuItems();
        this.admin = admin;

    }


    public void customerInterface() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCustomer Interface - Browse Menu, Place Orders");
            System.out.println("1. Browse Menu");
            System.out.println("2. Cart Operations");
            System.out.println("3. Order Tracking");
            System.out.println("4. Item Reviews");
            System.out.println("5. Become VIP Member");
            System.out.println("6. Exit to Main Menu");

            System.out.print("Enter your choice: ");
            int choice = getIntInput(scanner);

            switch (choice) {
                case 1 -> browseMenu(scanner);
                case 2 -> cartOperations(scanner);
                case 3 -> orderTracking(scanner);
                case 4 -> itemReviews(scanner);
                case 5 -> becomeVIP();
                case 6 -> {
                    System.out.println("Exit to main menu");
                    return; // Exit the loop and return to the main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Browse menu operations
    private void browseMenu(Scanner scanner) {
        System.out.println("\nBrowse Menu");
        System.out.println("1. View All Items");
        System.out.println("2. Search for an Item");
        System.out.println("3. Filter by Category");
        System.out.println("4. Sort by Price");
        System.out.print("Enter your choice: ");
        int choice = getIntInput(scanner);

        switch (choice) {
            case 1 -> viewAllItems();
            case 2 -> searchItem(scanner);
            case 3 -> filterByCategory(scanner);
            case 4 -> sortByPrice();
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    // Cart operations
    private void cartOperations(Scanner scanner) {
        System.out.println("\nCart Operations");
        System.out.println("1. Add Item to Cart");
        System.out.println("2. Modify Item Quantity");
        System.out.println("3. Remove Item from Cart");
        System.out.println("4. View Total");
        System.out.println("5. Checkout");
        System.out.print("Enter your choice: ");
        int choice = getIntInput(scanner);

        switch (choice) {
            case 1 -> addItemToCart(scanner);
            case 2 -> modifyItemQuantity(scanner);
            case 3 -> removeItemFromCart(scanner);
            case 4 -> viewCartTotal();
            case 5 -> checkout(admin);
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    // Order tracking operations
    private void orderTracking(Scanner scanner) {
        System.out.println("\nOrder Tracking");
        System.out.println("1. View Order Status");
        System.out.println("2. Cancel Order");
        System.out.println("3. View Order History");
        System.out.print("Enter your choice: ");
        int choice = getIntInput(scanner);

        switch (choice) {
            case 1 -> viewOrderStatus(scanner);
            case 2 -> cancelOrder(scanner);
            case 3 -> viewOrderHistory();
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    @Override
    public boolean isVIP() {
        return vipStatus;
    }

    public void becomeVIP() {
        if (this.vipStatus) {
            System.out.println("You are already a VIP customer!");
        } else {
            // Logic to handle payment for VIP status
            if (processPayment(VIP_COST)) {
                this.vipStatus = true;
                System.out.println("You are now a VIP customer!");
            } else {
                System.out.println("Payment failed. Unable to become a VIP.");
            }
        }
    }


    // Implement a simple payment processing method (stub)
    private boolean processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);

        // Simulating payment processing
        System.out.printf("Processing payment of %.2f...%n", amount);
        System.out.print("Enter your credit card number: ");
        String cardNumber = scanner.nextLine().trim();

        // Basic validation of the card number
        if (!isValidCardNumber(cardNumber)) {
            System.out.println("Invalid credit card number. Payment failed.");
            return false;
        }

        System.out.print("Enter expiration date (MM/YY): ");
        String expirationDate = scanner.nextLine().trim();


        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine().trim();
        if (!isValidCVV(cvv)) {
            System.out.println("Invalid CVV. Payment failed.");
            return false;
        }

        System.out.println("Payment processed successfully!");
        return true;
    }
    private boolean isValidCardNumber(String cardNumber) {
        // Simple check: must be 16 digits
        return cardNumber.matches("\\d{16}");
    }

    private boolean isValidCVV(String cvv) {
        return cvv.matches("\\d{3}");
    }
    // View all menu items
    private void viewAllItems() {
        System.out.println("Displaying all menu items:");
        viewAllItemsInGUI();
        List<MenuItem> menuItems = menu.getMenuItems();
        if (menuItems == null || menuItems.isEmpty()) {
            System.out.println("The menu is currently empty.");
        } else {
            for (MenuItem item : menuItems) {
                System.out.printf("%s - Price: %.2f, Available: %s%n",
                        item.getName(),
                        item.getPrice(),
                        item.isAvailable() ? "Yes" : "No");
            }
        }
    }

    public void viewAllItemsInGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Canteen Menu");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setLayout(new BorderLayout());

            // Add "Category" to column names
            String[] columnNames = {"Name", "Category", "Price", "Availability"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable menuTable = new JTable(tableModel);
            menuTable.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(menuTable);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Add menu items to the table, including the category
            for (MenuItem item : menuItems) {
                tableModel.addRow(new Object[]{
                        item.getName(),
                        item.getCategory(),
                        item.getPrice(),
                        item.isAvailable() ? "Available" : "Unavailable"
                });
            }

            // Set table font and row height for better appearance
            menuTable.setFont(new Font("Arial", Font.PLAIN, 14));
            menuTable.setRowHeight(25);

            // Colorize table rows and columns
            menuTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (isSelected) {
                        component.setBackground(new Color(173, 216, 230)); // Light blue for selected row
                        component.setForeground(Color.BLACK);
                    } else if (row % 2 == 0) {
                        component.setBackground(new Color(240, 255, 255)); // Light cyan for even rows
                        component.setForeground(Color.BLACK);
                    } else {
                        component.setBackground(Color.WHITE); // White for odd rows
                        component.setForeground(Color.BLACK);
                    }

                    if (column == 3) { // Availability column
                        String availability = (String) value;
                        if (availability.equalsIgnoreCase("Unavailable")) {
                            component.setForeground(Color.RED); // Red for unavailable items
                        } else {
                            component.setForeground(Color.GREEN.darker()); // Dark green for available items
                        }
                    }
                    return component;
                }
            });

            // Set table header styling
            JTableHeader header = menuTable.getTableHeader();
            header.setFont(new Font("Arial", Font.BOLD, 16));
            header.setBackground(new Color(135, 206, 250)); // Sky blue for header
            header.setForeground(Color.BLACK);

            frame.setVisible(true);
        });
    }


    // Search for an item
    private void searchItem(Scanner scanner) {
        System.out.print("Enter item name to search: ");
        String itemName = scanner.nextLine();
        System.out.println("Searching for item: " + itemName);

        boolean itemFound = false;

        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                System.out.println("Item found: " + item);
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            System.out.println("Item '" + itemName + "' not found in the menu.");
        }
    }



    private void filterByCategory(Scanner scanner) {
        System.out.print("Enter category to filter by: ");
        String category = scanner.nextLine().trim();
        System.out.println("Filtering by category: " + category);

        boolean categoryFound = false;

        for (MenuItem item : menuItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                System.out.println(item);
                categoryFound = true;
            }
        }
        if (!categoryFound) {
            System.out.println("No items found in the category: " + category);
        }
    }



    // Sort by price
    private void sortByPrice() {
        System.out.println("Sorting items by price...");

        try {
            if (menuItems.isEmpty()) {
                System.out.println("The menu is currently empty. No items to sort.");
                return;
            }

            menuItems.sort((item1, item2) -> Double.compare(item1.getPrice(), item2.getPrice()));

            System.out.println("Menu items sorted by price:");
            for (MenuItem item : menuItems) {
                System.out.println(item);
            }

        } catch (Exception e) {
            System.out.println("An error occurred while sorting the items: " + e.getMessage());
        }
    }


    // Add item to cart
    private void addItemToCart(Scanner scanner) {
        System.out.print("Enter item name to add to cart: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = getIntInput(scanner);

        if (quantity <= 0) {
            System.out.println("Invalid quantity. Please enter a positive number.");
            return;
        }

        // Search for the item in the menu
        MenuItem selectedItem = null;
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem == null) {
            System.out.println("Item '" + itemName + "' not found in the menu.");
            return;
        }

        if (!selectedItem.isAvailable()) {
            System.out.println("The item '" + itemName + "' is currently unavailable.");
            return;
        }

        CartItem cartItem = new CartItem(selectedItem, quantity);

        CartManager cartManager = new CartManager();
        List<CartItem> currentCart = cartManager.readCartFromFile();

        currentCart.add(cartItem);

        cartManager.writeCartToFile(currentCart);
        cart.add(cartItem);
        System.out.println("Added " + quantity + " of '" + itemName + "' to cart successfully.");
    }



    private void modifyItemQuantity(Scanner scanner) {
        try {
            System.out.print("Enter item name to modify quantity: ");
            String itemName = scanner.nextLine();
            System.out.print("Enter new quantity: ");
            int quantity = getIntInput(scanner);

            boolean itemFound = false;

            for (CartItem cartItem : cart) {
                if (cartItem.getItem().getName().equalsIgnoreCase(itemName)) {
                    cartItem.setQuantity(quantity);
                    System.out.println("Updated quantity of " + itemName + " to " + quantity + ".");
                    itemFound = true;
                    break;
                }
            }


            if (!itemFound) {
                System.out.println("Item '" + itemName + "' not found in the cart.");
            }
        } catch (Exception e) {
            System.out.println("Error updating item quantity: " + e.getMessage());
        }
    }


    // Remove item from cart
    private void removeItemFromCart(Scanner scanner) {
        try {
            System.out.print("Enter item name to remove from cart: ");
            String itemName = scanner.nextLine();

            // Retrieve the current cart from the file
            CartManager cartManager = new CartManager();
            List<CartItem> currentCart = cartManager.readCartFromFile();

            boolean itemRemoved = false;
            // Iterate over the cart to find and remove the item
            for (int i = 0; i < currentCart.size(); i++) {
                CartItem cartItem = currentCart.get(i);

                if (cartItem.getItem().getName().equalsIgnoreCase(itemName)) {
                    currentCart.remove(i);
                    System.out.println(itemName + " has been removed from the cart.");
                    itemRemoved = true;
                    break;
                }
            }

            // If item is not found, display an error message
            if (!itemRemoved) {
                System.out.println("Item '" + itemName + "' not found in the cart.");
            } else {
                // Write the updated cart back to the file
                cartManager.writeCartToFile(currentCart);
            }
        } catch (Exception e) {
            System.out.println("Error removing item from cart: " + e.getMessage());
        }
    }




    private void viewCartTotal() {
        double totalAmount = 0.0;

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");

            for (CartItem cartItem : cart) {
                double itemTotal = cartItem.getItem().getPrice() * cartItem.getQuantity();
                totalAmount += itemTotal;
                System.out.printf("%s - Quantity: %d, Subtotal: %.2f%n",
                        cartItem.getItem().getName(),
                        cartItem.getQuantity(),
                        itemTotal);
            }

            System.out.printf("Total amount: %.2f%n", totalAmount);
        }
    }



    private void checkout(Admin admin) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Please add items to your cart before checking out.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Proceeding to checkout...");
        viewCartTotal();

        // Collect payment details
        System.out.print("Enter payment method (e.g., Credit Card, PayPal): ");
        String paymentMethod = scanner.nextLine();

        System.out.print("Enter payment details (e.g., card number or account): ");
        String paymentDetails = scanner.nextLine();

        System.out.print("Enter delivery address: ");
        String deliveryAddress = scanner.nextLine();

        System.out.print("Enter contact number for delivery: ");
        String contactNumber = scanner.nextLine();

        double totalAmount = calculateTotalAmount();
        System.out.printf("Total Amount: %.2f%n", totalAmount);

        System.out.printf("Is VIP Customer: %b%n", isVIP());


        if (isVIP()) {
            totalAmount *= 0.95;
            System.out.printf("Congratulations! You receive a 5%% discount as a VIP customer.%n");
            System.out.printf("Discounted Total Amount: %.2f%n", totalAmount);
        }

        // Confirmation message
        System.out.println("\nOrder Summary:");
        System.out.printf("Payment Method: %s%n", paymentMethod);
        System.out.printf("Payment Details: %s%n", paymentDetails);
        System.out.printf("Delivery Address: %s%n", deliveryAddress);
        System.out.printf("Contact Number: %s%n", contactNumber);
        System.out.printf("Total Amount after any discounts: %.2f%n", totalAmount);

        System.out.print("Confirm order (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {

            System.out.println("Thank you for your order! Your items will be delivered soon.");
            int orderId = generateOrderId();
            for (CartItem cartItem : cart) {
                Order order = new Order(generateOrderId(), cartItem.getItem(),cartItem.getQuantity());
                orderList.add(order);
                admin.addOrder(order); // Add to admin's global order list
            }
            // Save order history for the current user
            OrderHistoryManager.saveOrderHistory(getUsername(), orderList);
            System.out.println("Your Order No: " + orderId);
            cart.clear();
        } else {
            System.out.println("Order has been cancelled.");
        }
    }
    private int generateOrderId() {
        return orderList.size() + 1;
    }

    private double calculateTotalAmount() {
        double totalAmount = 0.0;
        for (CartItem cartItem : cart) {
            totalAmount += cartItem.getItem().getPrice() * cartItem.getQuantity();
        }
        return totalAmount;
    }


    private void viewOrderStatus(Scanner scanner) {
        System.out.print("Enter order No to view status: ");
        int orderId = getIntInput(scanner);
        Order order = findOrderByIdFromList(orderId);
        if (order != null) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Item: " + order.getMenuItem().getName());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Status Updates:");
            for (Order.OrderStatusUpdate update : order.getStatusUpdates()) {
                System.out.printf("- %s at %s%n",
                        update.getStatus(),
                        formatTimestamp(update.getTimestamp()));
            }
        } else {
            System.out.println("Order not found.");
        }
    }


    // Helper method to find an order by ID from the orderList
    private Order findOrderByIdFromList(int orderId) {
        for (Order order : orderList) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }


    private void cancelOrder(Scanner scanner) {
        System.out.print("Enter order ID to cancel: ");
        int orderId = getIntInput(scanner);


        Order order = findOrderByIdFromList(orderId);
        if (order != null) {
            if (order.cancelOrder()) {
                System.out.println("Order " + orderId + " has been cancelled.");
            } else {
                System.out.println("Order " + orderId + " cannot be cancelled in its current state.");
            }
        } else {
            System.out.println("Order not found.");
        }
    }


    private void viewOrderHistory() {
        System.out.println("Displaying order history...");
        if (orderList.isEmpty()) {
            System.out.println("You have no past orders.");
        } else {
            for (Order order : orderList) {
                System.out.println(order);
                System.out.println("Status Updates:");
                for (Order.OrderStatusUpdate update : order.getStatusUpdates()) {
                    System.out.printf("- %s at %s%n",
                            update.getStatus(),
                            formatTimestamp(update.getTimestamp()));
                }
                System.out.println();
            }
        }
    }


    private String formatTimestamp(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
    }

    private void itemReviews(Scanner scanner) {
        System.out.println("\nItem Reviews");
        System.out.println("1. Provide Review");
        System.out.println("2. View Reviews");
        System.out.print("Enter your choice: ");
        int choice = getIntInput(scanner);

        switch (choice) {
            case 1 -> provideReview(scanner);
            case 2 -> viewReviews(scanner);
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void provideReview(Scanner scanner) {
        System.out.print("Enter the item name to review: ");
        String itemName = scanner.nextLine();
        MenuItem selectedItem = findMenuItemByName(itemName);

        if (selectedItem != null) {
            System.out.print("Enter your review: ");
            String review = scanner.nextLine();
            itemReviews.computeIfAbsent(selectedItem, k -> new ArrayList<>()).add(review);
            System.out.println("Thank you for your review!");
        } else {
            System.out.println("Item not found.");
        }
    }

    private void viewReviews(Scanner scanner) {
        System.out.print("Enter the item name to view reviews: ");
        String itemName = scanner.nextLine();
        MenuItem selectedItem = findMenuItemByName(itemName);

        if (selectedItem != null) {
            List<String> reviews = itemReviews.get(selectedItem);
            if (reviews != null && !reviews.isEmpty()) {
                System.out.println("Reviews for " + selectedItem.getName() + ":");
                for (String review : reviews) {
                    System.out.println("- " + review);
                }
            } else {
                System.out.println("No reviews for this item yet.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }


    private MenuItem findMenuItemByName(String itemName) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }


    private int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }


}
