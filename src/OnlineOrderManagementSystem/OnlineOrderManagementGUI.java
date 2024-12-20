package OnlineOrderManagementSystem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class OnlineOrderManagementGUI {

    private OrderManagement orderManagement;

    public OnlineOrderManagementGUI() {
        orderManagement = new OrderManagement();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Online Order Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        //create buttons
        JButton addProductButton = new JButton("Add Product");
        JButton addCustomerButton = new JButton("Add Customer");
        JButton createOrderButton = new JButton("Create Order");
        JButton listProductsButton = new JButton("List Procducts");
        JButton listCustomerButton = new JButton("List Customers");
        JButton listOrdersButton = new JButton("List Orders");

        //Panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.pink);
        panel.add(addProductButton);
        panel.add(addCustomerButton);
        panel.add(createOrderButton);
        panel.add(listProductsButton);
        panel.add(listCustomerButton);
        panel.add(listOrdersButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

        //button actions
        addProductButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Product Id: ");
            String name = JOptionPane.showInputDialog("Enter Product Name: ");
            String priceStr = JOptionPane.showInputDialog("Enter Product price: ");
            String stockStr = JOptionPane.showInputDialog("Enter Product Stock: ");
            try {
                double price = Double.parseDouble(priceStr);
                int stock = Integer.parseInt(stockStr);
                Product product = new Product(id, name, price, stock);
                orderManagement.addProduct(product);
                JOptionPane.showMessageDialog(frame, "Product added successfully");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ipout. Please enter numbers for price and stock.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addCustomerButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Customer Id: ");
            String name = JOptionPane.showInputDialog("Enter Customer Name: ");
            String phone = JOptionPane.showInputDialog("Enter Customer Phone: ");
            String email = JOptionPane.showInputDialog("Enter Customer Email: ");
            orderManagement.addCustomer(new Customer(id, name, phone, email));
            JOptionPane.showMessageDialog(frame, "Customer added successfully.");
        });

        createOrderButton.addActionListener(e -> {
            String customerId = JOptionPane.showInputDialog("Enter Customer Id: ");
            ArrayList<Product> orderProducts = new ArrayList<>();
            while (true) {
                String productId = JOptionPane.showInputDialog("Enter Product Id to add to order(or type 'done' to finish");
                if (productId.equalsIgnoreCase("done")) {
                    break;
                }
                String quantityStr = JOptionPane.showInputDialog("Enter Quantity: ");
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    Product product = orderManagement.findProductById(productId);
                    if (product != null && product.getStock() >= quantity) {
                        orderProducts.add(new Product(product.getProductId(), product.getProductName(), product.getPrice(), quantity));
                        orderManagement.updateStock(productId, quantity);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Product not found or insufficient stock. ", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number for quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (!orderProducts.isEmpty()) {
                Customer customer = orderManagement.findCustomerById(customerId);
                if (customer != null) {
                    double totalCost = orderProducts.stream().mapToDouble(p -> p.getPrice() * p.getStock()).sum();
                    Order order = new Order("O" +(orderManagement.displayAllOrders().size() + 1), customer, orderProducts, totalCost);
                    orderManagement.createOrder(order);
                    JOptionPane.showMessageDialog(frame, "Order created successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Customer not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

        listProductsButton.addActionListener(e -> {
            List<Product> products = orderManagement.displayAllProducts();
            StringBuilder message = new StringBuilder("Products in the Systen:\n");
            for (Product product : products) {
                message.append(product.getProductName()).append(" (ID: ").append(product.getProductId()).append(", Price: ").append(product.getPrice()).append(", Stock: ").append(product.getStock()).append(")\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString());
        });

        listCustomerButton.addActionListener(e -> {
            List<Customer> customers = orderManagement.displayAllCustomers();
            StringBuilder message = new StringBuilder("Customers in the System:\n");
            for (Customer customer : customers) {
                message.append(customer.getCustomerName()).append(" (ID: ").append(customer.getCustomerId()).append(", Phone: ").append(customer.getPhone()).append(", Email: ").append(customer.getEmail()).append(")\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString());
        });

        listOrdersButton.addActionListener(e -> {
            try{
                List<Order> orders = orderManagement.displayAllOrders();
                if(orders == null || orders.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "No orders found.");
                    return;
                }
                StringBuilder message = new StringBuilder("Orders in the system: \n");
                for(Order order:orders){
                    message.append("Order Id: ").append(order.getOrderId())
                            .append(", Customer: ").append(order.getCustomer())
                            .append(", Total cost: ").append(order.getTotalCost())
                            .append(", Products:\n");
                    List<Product> products = order.getProducts();
                    if(products != null && !products.isEmpty()){
                        for(Product product:products){
                            message.append(" - ").append(product.getProductName())
                                    .append(" x ").append(product.getStock()).append("\n");
                        }
                    }else{
                        message.append(" No products in the order. \n");
                    }
                    message.append("\n");
                }
                JOptionPane.showMessageDialog(frame, message.toString());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(frame, "An error occurred while retrieving orders: " +ex.getMessage());
            }
            
        });
    }

    public static void main(String[] args) {
        new OnlineOrderManagementGUI();
    }
}
