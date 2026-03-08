package dev.langchain4j.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order entity
 */
@Data
@TableName("orders")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;
    /** Order number in format SS-xxxxx */
    private String orderNumber;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    /** Order status: PENDING/PAID/SHIPPED/COMPLETED/CANCELLED/REFUNDING */
    private String status;
    private String shippingAddress;
    private String trackingNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
