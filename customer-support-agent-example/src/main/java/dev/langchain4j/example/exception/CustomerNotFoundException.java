package dev.langchain4j.example.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String name) {
        super("Customer " + name + " not found");
    }
}
