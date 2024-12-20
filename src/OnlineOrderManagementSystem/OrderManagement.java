package OnlineOrderManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class OrderManagement {

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public OrderManagement() {
    }

    //add a new product 
    public void addProduct(Product product) {
        products.add(product);
    }

    //display all products
    public List<Product> displayAllProducts() {
        return products;
    }

    //find product by id
    public Product findProductById(String id) {
        for (Product product : products) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    //update stock quantity of a product based in id
    public void updateStock(String productId, int quantity) {
        Product product = findProductById(productId);
        if (product != null) {
            if (product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
            } else {
                System.out.println("Not enough inventory for id " + productId);
            }
        } else {
            System.out.println("Product doesn't exist with id " + productId);
        }
    }

    //aad a customer
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //display all customers
    public List<Customer> displayAllCustomers() {
        return customers;
    }
    
    //find customer by id
    public Customer findCustomerById(String id){
        for(Customer customer:customers){
            if(customer.getCustomerId().equals(id)){
                return customer;
            }
        }
        return null;
    }
    
    //create order
    public void createOrder(Order order){
        orders.add(order);
    }
    
    //display all orders
    public List<Order> displayAllOrders(){
        return orders;
    }
}
