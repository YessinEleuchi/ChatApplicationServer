package com.chatapplication.chat.repository;

import com.chatapplication.chat.model.Message;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends Neo4jRepository<Message, String> {

    @Query("""
    MATCH (u:User {id: $userId})-[:SENT_BY]->(m:Message)
    RETURN m ORDER BY m.timestamp ASC
    """)
    List<Message> findHistoryByUserId(String userId);
}
