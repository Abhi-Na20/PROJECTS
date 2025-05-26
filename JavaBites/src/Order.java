import java.util.ArrayList;
import java.util.List;

public class Order {
    static int nextOrderId = 1;
    int orderId;
    String customerPhoneNumber;
    List<MenuItem> items;
    double totalAmount;
    double finalAmountDue;
    String feedback;
    boolean isPaid;
    String paymentMethod;
    String promotionAppliedInfo;

    public Order(String customerPhoneNumber) {
        this.orderId = nextOrderId++;
        this.customerPhoneNumber = customerPhoneNumber;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.finalAmountDue = 0.0;
        this.feedback = "";
        this.isPaid = false;
        this.promotionAppliedInfo = "";
    }

    public void addItem(MenuItem item) {
        items.add(item);
        calculateTotalAmount();
    }

    public void calculateTotalAmount() {
        this.totalAmount = 0;
        for (MenuItem item : items) {
            this.totalAmount += item.getPrice();
        }
        this.finalAmountDue = this.totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getFinalAmountDue() {
        return finalAmountDue;
    }

    public void setFinalAmountDue(double finalAmountDue) {
        this.finalAmountDue = finalAmountDue;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid, String method) {
        isPaid = paid;
        this.paymentMethod = method;
    }
    public String getPromotionAppliedInfo() { return promotionAppliedInfo; }
    public void setPromotionAppliedInfo(String promotionAppliedInfo) { this.promotionAppliedInfo = promotionAppliedInfo; }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\nItems:\n");
        if (items.isEmpty()) {
            sb.append("  No items in order.\n");
        } else {
            for (MenuItem item : items) {
                sb.append("  - ").append(item.getName()).append(" ($").append(String.format("%.2f", item.getPrice())).append(")\n");
            }
        }
        sb.append("Original Total: $").append(String.format("%.2f", totalAmount)).append("\n");
        if (!promotionAppliedInfo.isEmpty()) {
            sb.append("Promotions: ").append(promotionAppliedInfo).append("\n");
        }
        sb.append("Final Amount Due: $").append(String.format("%.2f", finalAmountDue)).append("\n");
        sb.append("Status: ").append(isPaid ? "Paid via " + paymentMethod : "Pending Payment").append("\n");
        if (feedback != null && !feedback.isEmpty()) {
            sb.append("Feedback: ").append(feedback).append("\n");
        }
        return sb.toString();
    }
}