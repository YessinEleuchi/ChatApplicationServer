package com.chatapplication.security;

import java.util.UUID;

/**
 * Represents an authenticated user in the security context.
 */
public record AuthenticatedUser(String userId, String email) {

    public UUID getUserIdAsUUID() {
        return UUID.fromString(userId);
    }
}
