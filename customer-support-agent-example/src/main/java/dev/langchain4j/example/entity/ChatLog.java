package dev.langchain4j.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Chat log entity for persisting conversation history
 */
@Data
@TableName("chat_log")
public class ChatLog {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String sessionId;
    /** Role: user/assistant */
    private String role;
    private String content;
    private LocalDateTime createdAt;
}
