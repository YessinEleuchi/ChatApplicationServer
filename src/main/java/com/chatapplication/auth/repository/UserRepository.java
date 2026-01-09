package com.chatapplication.auth.repository;

import com.chatapplication.auth.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {

    Optional<User> findByEmail(String email);
    User findUserById(String userId);

    boolean existsByEmail(String email);
}
