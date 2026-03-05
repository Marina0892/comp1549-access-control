package accesscontrol;

import java.time.LocalDateTime;

/**
 * Represents a single access log entry.
 */
public class AccessLogEntry {

    private final LocalDateTime timestamp;
    private final String userId;
    private final Role role;
    private final Resource resource;
    private final Action action;
    private final boolean allowed;

    public AccessLogEntry(String userId,
                          Role role,
                          Resource resource,
                          Action action,
                          boolean allowed) {

        this.timestamp = LocalDateTime.now();
        this.userId = userId;
        this.role = role;
        this.resource = resource;
        this.action = action;
        this.allowed = allowed;
    }

    @Override
    public String toString() {
        return timestamp + " | " +
               userId + " | " +
               role + " | " +
               resource + " | " +
               action + " | " +
               (allowed ? "ALLOW" : "REFUSE");
    }
}