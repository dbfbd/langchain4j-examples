package dev.langchain4j.example;

import dev.langchain4j.example.service.ChatLogService;
import dev.langchain4j.service.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the Smart Shop customer support chat endpoint
 */
@RestController
@RequestMapping("/api")
public class CustomerSupportAgentController {

    private final CustomerSupportAgent customerSupportAgent;
    private final ChatLogService chatLogService;

    public CustomerSupportAgentController(CustomerSupportAgent customerSupportAgent, ChatLogService chatLogService) {
        this.customerSupportAgent = customerSupportAgent;
        this.chatLogService = chatLogService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        // Persist user message
        chatLogService.save(request.sessionId(), "user", request.userMessage());

        // Get AI response
        Result<String> result = customerSupportAgent.answer(request.sessionId(), request.userMessage());
        String reply = result.content();

        // Persist assistant message
        chatLogService.save(request.sessionId(), "assistant", reply);

        return new ChatResponse(reply);
    }

    public record ChatRequest(String sessionId, String userMessage) {}
    public record ChatResponse(String reply) {}
}
