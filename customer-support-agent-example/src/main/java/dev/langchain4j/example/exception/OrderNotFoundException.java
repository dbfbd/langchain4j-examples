package dev.langchain4j.example.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String orderNumber) {
        super("Order " + orderNumber + " not found");
    }
}
