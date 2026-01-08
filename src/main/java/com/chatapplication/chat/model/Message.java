package com.chatapplication.chat.model;

import com.chatapplication.auth.model.User;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.Instant;

@Node("Message")
public class Message {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    // Means: (User)-[:SENT_BY]->(Message)
    @Relationship(type = "SENT_BY", direction = Relationship.Direction.INCOMING)
    private User sender;

    private String prompt;
    private String response;
    private Instant timestamp;

    public Message() {}

    public Message(User sender, String prompt, String response, Instant timestamp) {
        this.sender = sender;
        this.prompt = prompt;
        this.response = response;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }

    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
