// AccessDecision.java
package accesscontrol;

/**
 * Represents the result of an access control decision.
 * Contains whether access is granted and an explanation message.
 */
public class AccessDecision {
    private final boolean granted;
    private final String message;

    public AccessDecision(boolean granted, String message) {
        this.granted = granted;
        this.message = message;
    }

    public boolean isGranted() {
        return granted;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "AccessDecision{granted=" + granted + ", message='" + message + "'}";
    }
}