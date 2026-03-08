package dev.langchain4j.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import dev.langchain4j.example.entity.Customer;
import dev.langchain4j.example.entity.Order;
import dev.langchain4j.example.exception.CustomerNotFoundException;
import dev.langchain4j.example.exception.OrderNotFoundException;
import dev.langchain4j.example.mapper.CustomerMapper;
import dev.langchain4j.example.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for order-related operations
 */
@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;

    public OrderService(OrderMapper orderMapper, CustomerMapper customerMapper) {
        this.orderMapper = orderMapper;
        this.customerMapper = customerMapper;
    }

    /**
     * Find the customer by name and phone, throw if not found
     */
    private Customer findCustomer(String customerName, String phone) {
        Customer customer = customerMapper.selectOne(
                new LambdaQueryWrapper<Customer>()
                        .eq(Customer::getName, customerName)
                        .eq(Customer::getPhone, phone)
        );
        if (customer == null) {
            throw new CustomerNotFoundException(customerName);
        }
        return customer;
    }

    /**
     * Get order details by order number, validating customer identity
     */
    public Order getOrderDetails(String orderNumber, String customerName, String phone) {
        Customer customer = findCustomer(customerName, phone);
        Order order = orderMapper.selectOne(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getOrderNumber, orderNumber)
                        .eq(Order::getCustomerId, customer.getId())
        );
        if (order == null) {
            throw new OrderNotFoundException(orderNumber);
        }
        return order;
    }

    /**
     * List all orders for a customer
     */
    public List<Order> listOrders(String customerName, String phone) {
        Customer customer = findCustomer(customerName, phone);
        return orderMapper.selectList(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getCustomerId, customer.getId())
                        .orderByDesc(Order::getCreatedAt)
        );
    }

    /**
     * Cancel an order (only allowed for PENDING or PAID status)
     */
    @Transactional
    public void cancelOrder(String orderNumber, String customerName, String phone) {
        Order order = getOrderDetails(orderNumber, customerName, phone);
        if ("CANCELLED".equals(order.getStatus()) || "COMPLETED".equals(order.getStatus())) {
            throw new IllegalStateException("Order " + orderNumber + " cannot be cancelled in status: " + order.getStatus());
        }
        if ("SHIPPED".equals(order.getStatus())) {
            throw new IllegalStateException("Order " + orderNumber + " has already been shipped and cannot be cancelled. Please request a return after delivery.");
        }
        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    /**
     * Request a refund for an order (only allowed for PAID, SHIPPED, or COMPLETED status)
     */
    @Transactional
    public void requestRefund(String orderNumber, String customerName, String phone) {
        Order order = getOrderDetails(orderNumber, customerName, phone);
        if ("CANCELLED".equals(order.getStatus()) || "REFUNDING".equals(order.getStatus())) {
            throw new IllegalStateException("Order " + orderNumber + " cannot be refunded in status: " + order.getStatus());
        }
        if ("PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("Order " + orderNumber + " has not been paid yet. Please cancel it instead.");
        }
        order.setStatus("REFUNDING");
        order.setUpdatedAt(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    /**
     * Get tracking information for an order
     */
    public String getTrackingInfo(String orderNumber) {
        Order order = orderMapper.selectOne(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getOrderNumber, orderNumber)
        );
        if (order == null) {
            throw new OrderNotFoundException(orderNumber);
        }
        if (order.getTrackingNumber() == null || order.getTrackingNumber().isEmpty()) {
            return "Order " + orderNumber + " (status: " + order.getStatus() + ") has not been shipped yet. No tracking number available.";
        }
        return "Order " + orderNumber + " tracking number: " + order.getTrackingNumber() + " (status: " + order.getStatus() + ")";
    }
}
