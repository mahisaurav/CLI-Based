import java.io.*;
import java.util.List;

public class OrderHistoryManager {

    private static final String ORDER_HISTORY_FILE = "order_history.txt"; // File to save order history

    // Method to save the order history for a user
    public static void saveOrderHistory(String username, List<Order> orders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_HISTORY_FILE, true))) {
            writer.write("Order History for " + username + ":\n");
            for (Order order : orders) {
                writer.write(order.getOrderId() + " | " + order.getMenuItem().getName() + " | " +
                        order.getMenuItem().getPrice() + " | Quantity: " + order.getQuantity() + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve the order history of a user
    public static void retrieveOrderHistory(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_HISTORY_FILE))) {
            String line;
            boolean isUserOrderHistory = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Order History for " + username)) {
                    isUserOrderHistory = true;
                    System.out.println(line);
                } else if (isUserOrderHistory && line.trim().isEmpty()) {
                    break;  // Stop reading when the next user's order history starts
                } else if (isUserOrderHistory) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
