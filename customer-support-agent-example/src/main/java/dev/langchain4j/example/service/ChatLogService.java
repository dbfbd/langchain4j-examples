package dev.langchain4j.example.service;

import dev.langchain4j.example.entity.ChatLog;
import dev.langchain4j.example.mapper.ChatLogMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service for persisting chat conversation logs
 */
@Service
public class ChatLogService {

    private final ChatLogMapper chatLogMapper;

    public ChatLogService(ChatLogMapper chatLogMapper) {
        this.chatLogMapper = chatLogMapper;
    }

    /**
     * Save a chat message to the database
     *
     * @param sessionId the session identifier
     * @param role      "user" or "assistant"
     * @param content   message content
     */
    public void save(String sessionId, String role, String content) {
        ChatLog log = new ChatLog();
        log.setSessionId(sessionId);
        log.setRole(role);
        log.setContent(content);
        log.setCreatedAt(LocalDateTime.now());
        chatLogMapper.insert(log);
    }
}
