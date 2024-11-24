import java.util.ArrayList;
import java.util.List;

class Order {
    private int orderId;
    private MenuItem menuItem;
    private OrderStatus status;
    private List<OrderStatusUpdate> statusUpdates;
    private String specialRequest; // Field to store special request
    private int quantity;

    public Order(int orderId, MenuItem menuItem,int quantity) {
        this.orderId = orderId;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.status = OrderStatus.PENDING; // Default status
        this.statusUpdates = new ArrayList<>();
        addStatusUpdate(OrderStatus.RECEIVED); // Add the initial status update
    }

    public int getOrderId() {
        return orderId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        addStatusUpdate(status); // Add a new status update to the list
    }

    public boolean cancelOrder() {
        if (status == OrderStatus.RECEIVED || status == OrderStatus.PENDING) {
            this.status = OrderStatus.CANCELLED;
            addStatusUpdate(OrderStatus.CANCELLED); // Add the cancellation status update
            return true;
        }
        return false;
    }

    // Method to set a special request for the order
    public void setSpecialRequest(String request) {
        this.specialRequest = request;
    }

    // Method to check if there is a special request
    public boolean hasSpecialRequest() {
        return specialRequest != null && !specialRequest.isEmpty();
    }

    private void addStatusUpdate(OrderStatus newStatus) {
        statusUpdates.add(new OrderStatusUpdate(newStatus));
    }

    public List<OrderStatusUpdate> getStatusUpdates() {
        return statusUpdates;
    }

    public String getSpecialRequest() {
        return specialRequest; // Getter for special request
    }

    public enum OrderStatus {
        RECEIVED, PENDING, PREPARING, DELIVERED, CANCELLED, DENIED;
    }

    static class OrderStatusUpdate {
        private final OrderStatus status;
        private final long timestamp;

        public OrderStatusUpdate(OrderStatus status) {
            this.status = status;
            this.timestamp = System.currentTimeMillis();
        }

        public OrderStatus getStatus() {
            return status;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Item: " + menuItem.getName() +
                ", Status: " + status +
                (hasSpecialRequest() ? ", Special Request: " + specialRequest : "");
    }
}
