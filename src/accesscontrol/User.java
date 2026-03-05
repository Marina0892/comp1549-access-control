// User.java
package accesscontrol;

/**
 * Simple User model used for testing and demonstration.
 */
public class User {
    private final String userId;
    private final Role role;

    public User(String userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }
}