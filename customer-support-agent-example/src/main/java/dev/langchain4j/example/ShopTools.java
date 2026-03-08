package dev.langchain4j.example;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.example.entity.Order;
import dev.langchain4j.example.entity.Product;
import dev.langchain4j.example.service.CustomerService;
import dev.langchain4j.example.service.OrderService;
import dev.langchain4j.example.service.ProductService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * AI tools for the Smart Shop customer support agent.
 * These tools are invoked automatically by the AI based on user queries.
 */
@Component
public class ShopTools {

    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerService customerService;

    public ShopTools(OrderService orderService, ProductService productService, CustomerService customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Tool("Get order details by order number, customer name and phone")
    public Order getOrderDetails(String orderNumber, String customerName, String phone) {
        return orderService.getOrderDetails(orderNumber, customerName, phone);
    }

    @Tool("List all orders for a customer by name and phone")
    public List<Order> listOrders(String customerName, String phone) {
        return orderService.listOrders(customerName, phone);
    }

    @Tool("Cancel an order by order number, customer name and phone")
    public void cancelOrder(String orderNumber, String customerName, String phone) {
        orderService.cancelOrder(orderNumber, customerName, phone);
    }

    @Tool("Request a refund for an order by order number, customer name and phone")
    public void requestRefund(String orderNumber, String customerName, String phone) {
        orderService.requestRefund(orderNumber, customerName, phone);
    }

    @Tool("Get product information by product name")
    public Product getProductInfo(String productName) {
        return productService.findByName(productName);
    }

    @Tool("Search products by category")
    public List<Product> searchProducts(String category) {
        return productService.findByCategory(category);
    }

    @Tool("Get shipping tracking information by order number")
    public String getTrackingInfo(String orderNumber) {
        return orderService.getTrackingInfo(orderNumber);
    }

    @Tool("Calculate discounted price for a product based on customer VIP level")
    public String calculateDiscount(String productName, String customerName, String phone) {
        Product product = productService.findByName(productName);
        if (product == null) {
            return "Product '" + productName + "' not found.";
        }
        var customer = customerService.findByNameAndPhone(customerName, phone);
        int vipLevel = customer.getVipLevel();
        double discountRate = switch (vipLevel) {
            case 1 -> 0.05;  // Silver: 5% off
            case 2 -> 0.10;  // Gold: 10% off
            case 3 -> 0.15;  // Diamond: 15% off
            default -> 0.0;  // Normal: no discount
        };
        String vipLabel = switch (vipLevel) {
            case 1 -> "Silver";
            case 2 -> "Gold";
            case 3 -> "Diamond";
            default -> "Regular";
        };
        BigDecimal originalPrice = product.getPrice();
        BigDecimal discountAmount = originalPrice.multiply(BigDecimal.valueOf(discountRate)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalPrice = originalPrice.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
        if (discountRate == 0.0) {
            return String.format("Product: %s | Original Price: $%.2f | VIP Level: %s | No discount applicable | Final Price: $%.2f",
                    product.getName(), originalPrice, vipLabel, finalPrice);
        }
        return String.format("Product: %s | Original Price: $%.2f | VIP Level: %s (%.0f%% off) | Discount: -$%.2f | Final Price: $%.2f",
                product.getName(), originalPrice, vipLabel, discountRate * 100, discountAmount, finalPrice);
    }
}
