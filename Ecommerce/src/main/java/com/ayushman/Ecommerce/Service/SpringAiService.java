package com.ayushman.Ecommerce.Service;

import com.ayushman.Ecommerce.LLMTools.OrderTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import com.ayushman.Ecommerce.LLMTools.ProductTool;

@Service
public class SpringAiService {

    private final ChatClient chatClient;

    public SpringAiService(ChatClient.Builder builder,
                           OrderTool orderTool,
                           ProductTool productTool) {

        this.chatClient = builder
                .defaultTools(orderTool, productTool)
                .build();
    }

    public String chat(String message) {

        return chatClient.prompt()
                .system("""
You are an ecommerce support assistant.
Never create fake orders.
For order, payment, email, status or razorpay related questions,
always use available tools.
If no data exists, say data not found in database.
""")
                .user(message)
                .call()
                .content();
    }
}