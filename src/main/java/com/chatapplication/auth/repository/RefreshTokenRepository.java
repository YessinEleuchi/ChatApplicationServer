package com.chatapplication.auth.repository;

import com.chatapplication.auth.model.RefreshTokenNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends Neo4jRepository<RefreshTokenNode, String> {

    Optional<RefreshTokenNode> findByToken(String token);

    @Query("MATCH (t:RefreshToken) WHERE t.userId = $userId DELETE t")
    void deleteByUserId(String userId);

    @Query("MATCH (t:RefreshToken) WHERE t.token = $token SET t.revoked = true")
    void revokeByToken(String token);
}
