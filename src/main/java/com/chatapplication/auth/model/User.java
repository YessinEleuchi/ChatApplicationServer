package com.chatapplication.auth.model;

import com.chatapplication.chat.model.Message;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.List;

@Node("User")
public class User {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    private String email;

    private String passwordHash;

    @Relationship(type = "HAS_REFRESH_TOKEN", direction = Relationship.Direction.OUTGOING)
    private List<RefreshTokenNode> refreshTokens = new ArrayList<>();
    @Relationship(type = "WROTE")
    private List<Message> messages = new ArrayList<>();

    public User() {
    }

    public User(String id, String email, String passwordHash) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<RefreshTokenNode> getRefreshTokens() {
        return refreshTokens;
    }

    public void setRefreshTokens(List<RefreshTokenNode> refreshTokens) {
        this.refreshTokens = refreshTokens;
    }
}
