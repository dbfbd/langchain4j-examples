package dev.langchain4j.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Customer entity
 */
@Data
@TableName("customer")
public class Customer {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String phone;
    /** VIP level: 0=normal, 1=silver, 2=gold, 3=diamond */
    private Integer vipLevel;
    private LocalDateTime createdAt;
}
