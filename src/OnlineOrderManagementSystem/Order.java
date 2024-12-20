
package OnlineOrderManagementSystem;

import java.util.List;

public class Order {
    private String orderId;
    private Customer customer;
    private List<Product> products;
    private double totalCost;

    public Order() {
    }

    public Order(String orderId, Customer customer, List<Product> products, double totalCost) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = products;
        this.totalCost = totalCost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", customer=" + customer + ", products=" + products + ", totalCost=" + totalCost + '}';
    }
    
}
