import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

class Admin extends User {
    private Menu menu;
    private List<Order> orders;
    private double totalSales;
    private Map<String, Integer> itemSalesCount;

    public Admin(String username, String password, Menu menu) {
        super(username, password);
        this.menu = menu;
        this.orders = new ArrayList<>();
        this.itemSalesCount = new HashMap<>();
    }

    public void adminInterface() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAdmin Interface - Manage Menu, Handle Orders, Generate Reports");
            System.out.println("1. Manage Menu Items");
            System.out.println("2. View Orders");
            System.out.println("3. Generate Daily Sales Report");
            System.out.println("4. Exit to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getIntInput(scanner);

            switch (choice) {
                case 1 -> manageMenuItems(scanner);
                case 2 -> viewOrders(scanner);
                case 3 -> generateDailySalesReport();
                case 4 -> {
                    System.out.println("Exiting to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageMenuItems(Scanner scanner) {
        System.out.println("\nManage Menu Items");
        System.out.println("1. Add Menu Item");
        System.out.println("2. Modify Menu Item");
        System.out.println("3. Remove Menu Item");
        System.out.print("Enter your choice: ");
        int choice = getIntInput(scanner);

        switch (choice) {
            case 1 -> addMenuItem(scanner);
            case 2 -> modifyMenuItem(scanner);
            case 3 -> removeMenuItem(scanner);
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addMenuItem(Scanner scanner) {
        System.out.print("Enter item ID: ");
        int id = getIntInput(scanner);
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item price: ");
        double price = getDoubleInput(scanner);
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();
        System.out.print("Is the item available? (true/false): ");
        boolean available = getBooleanInput(scanner);

        MenuItem newItem = new MenuItem(id, name, category, price, available);
        menu.addMenuItem(newItem);
        System.out.println("Menu item added successfully.");
    }

    private void modifyMenuItem(Scanner scanner) {
        System.out.print("Enter the name of the item to modify: ");
        String name = scanner.nextLine();
        MenuItem itemToModify = findMenuItemByName(name);

        if (itemToModify != null) {
            System.out.print("Enter new price (current: " + itemToModify.getPrice() + "): ");
            double newPrice = getDoubleInput(scanner);
            System.out.print("Is the item available? (current: " + itemToModify.isAvailable() + ") (true/false): ");
            boolean newAvailable = getBooleanInput(scanner);

            itemToModify.setPrice(newPrice);
            itemToModify.setAvailable(newAvailable);
            System.out.println("Menu item modified successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private void removeMenuItem(Scanner scanner) {
        System.out.print("Enter the name of the item to remove: ");
        String name = scanner.nextLine();
        MenuItem itemToRemove = findMenuItemByName(name);

        if (itemToRemove != null) {
            menu.removeMenuItem(itemToRemove);
            System.out.println("Menu item removed successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private void viewOrders(Scanner scanner) {
        System.out.println("\nOrder Management");
        System.out.println("1. View Pending Orders");
        System.out.println("2. Update Order Status");
        System.out.println("3. Process Refund");
        System.out.println("4. Handle Special Requests");
        System.out.print("Enter your choice: ");
        int choice = getIntInput(scanner);

        switch (choice) {
            case 1 -> viewPendingOrders();
            case 2 -> updateOrderStatus(scanner);
            case 3 -> processRefund(scanner);
            case 4 -> handleSpecialRequests(scanner);
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void viewPendingOrders() {
        System.out.println("\nPending Orders:");

        // Filter orders with status PENDING
        List<Order> pendingOrders = orders.stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.PENDING)
                .toList();

        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders at the moment.");
        } else {
            viewPendingOrdersInGUI();
            pendingOrders.forEach(System.out::println);
        }
    }
    public void viewPendingOrdersInGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pending Orders");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 400);
            frame.setLayout(new BorderLayout());

            // Add "Order Number", "Items Ordered", "Status" to column names
            String[] columnNames = {"Order Number", "Items Ordered", "Status"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable ordersTable = new JTable(tableModel);
            ordersTable.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(ordersTable);
            frame.add(scrollPane, BorderLayout.CENTER);

            // Filter orders with status PENDING
            List<Order> pendingOrders = orders.stream()
                    .filter(order -> order.getStatus() == Order.OrderStatus.PENDING)
                    .toList();

            if (pendingOrders.isEmpty()) {
                tableModel.addRow(new Object[]{"No pending orders at the moment.", "", ""});
            } else {
                // Add pending orders to the table
                for (Order order : pendingOrders) {
                    String itemsOrdered = order.getMenuItem().getName(); // Assuming there's a method to get items from the order
                    tableModel.addRow(new Object[]{
                            order.getOrderId(),
                            itemsOrdered,
                            order.getStatus().toString()  // Display the status of the order
                    });
                }
            }

            // Set table font and row height for better appearance
            ordersTable.setFont(new Font("Arial", Font.PLAIN, 14));
            ordersTable.setRowHeight(25);

            // Colorize table rows and columns
            ordersTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

                    if (column == 2) { // Status column
                        String status = (String) value;
                        if (status.equalsIgnoreCase("PENDING")) {
                            component.setForeground(Color.ORANGE); // Orange for pending status
                        } else {
                            component.setForeground(Color.GREEN.darker()); // Dark green for completed status
                        }
                    }
                    return component;
                }
            });

            // Set table header styling
            JTableHeader header = ordersTable.getTableHeader();
            header.setFont(new Font("Arial", Font.BOLD, 16));
            header.setBackground(new Color(135, 206, 250)); // Sky blue for header
            header.setForeground(Color.BLACK);

            frame.setVisible(true);
        });
    }


    private void updateOrderStatus(Scanner scanner) {
        System.out.print("Enter Order ID to update status: ");
        int orderId = getIntInput(scanner);
        Order order = findOrderById(orderId);

        if (order != null) {
            System.out.println("Current status: " + order.getStatus());
            System.out.println("1. Preparing");
            System.out.println("2. Delivered");
            System.out.println("3. Cancelled");
            System.out.print("Enter new status choice: ");
            int statusChoice = getIntInput(scanner);

            switch (statusChoice) {
                case 1 -> order.setStatus(Order.OrderStatus.PREPARING);
                case 2 -> order.setStatus(Order.OrderStatus.DELIVERED);
                case 3 -> order.setStatus(Order.OrderStatus.CANCELLED);
                default -> System.out.println("Invalid status choice.");
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    private void processRefund(Scanner scanner) {
        System.out.print("Enter Order ID to process refund: ");
        int orderId = getIntInput(scanner);
        Order order = findOrderById(orderId);

        if (order != null && order.getStatus() == Order.OrderStatus.CANCELLED) {
            System.out.println("Refund processed for Order ID: " + orderId);
            totalSales -= order.getMenuItem().getPrice();
        } else {
            System.out.println("Refund cannot be processed. Order is either not found or not cancelled.");
        }
    }

    private void handleSpecialRequests(Scanner scanner) {
        System.out.println("\nHandling Special Requests:");
        for (Order order : orders) {
            if (order.hasSpecialRequest()) {
                System.out.println("Order ID: " + order.getOrderId() + " - Special Request: " + order.getSpecialRequest());
            }
        }
    }

    private void generateDailySalesReport() {
        System.out.println("\nDaily Sales Report");
        if (orders.isEmpty()) {
            System.out.println("No orders have been processed today.");
            return;
        }


        System.out.printf("Total Sales: ₹%.2f%n", totalSales);
        System.out.println("Total Orders: " + orders.size());
        System.out.println("Most Popular Items:");
        itemSalesCount.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> System.out.printf("%s: %d sales%n", entry.getKey(), entry.getValue()));
    }

    public void addOrder(Order order) {
        orders.add(order);
        totalSales += order.getMenuItem().getPrice() * order.getQuantity();
        itemSalesCount.merge(order.getMenuItem().getName(), order.getQuantity(), Integer::sum);
    }


    private MenuItem findMenuItemByName(String itemName) {
        return menu.getMenuItems().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    private Order findOrderById(int orderId) {
        return orders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
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

    private double getDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid price: ");
            }
        }
    }

    private boolean getBooleanInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("true")) {
                return true;
            } else if (input.equalsIgnoreCase("false")) {
                return false;
            } else {
                System.out.print("Invalid input. Please enter 'true' or 'false': ");
            }
        }
    }
}
