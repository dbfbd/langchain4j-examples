package dev.langchain4j.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import dev.langchain4j.example.entity.Customer;
import dev.langchain4j.example.exception.CustomerNotFoundException;
import dev.langchain4j.example.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

/**
 * Service for customer-related operations
 */
@Service
public class CustomerService {

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    /**
     * Find a customer by name and phone number
     */
    public Customer findByNameAndPhone(String name, String phone) {
        Customer customer = customerMapper.selectOne(
                new LambdaQueryWrapper<Customer>()
                        .eq(Customer::getName, name)
                        .eq(Customer::getPhone, phone)
        );
        if (customer == null) {
            throw new CustomerNotFoundException(name);
        }
        return customer;
    }
}
