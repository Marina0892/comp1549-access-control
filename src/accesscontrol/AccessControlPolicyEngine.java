package accesscontrol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Policy engine that evaluates access requests based on RBAC rules.
 * Fault tolerance: denies access if user/resource/action is null.
 * Also logs every access attempt.
 */
public class AccessControlPolicyEngine {

    private final List<AccessLogEntry> logs = new ArrayList<>();

    public AccessDecision evaluateAccess(User user, Resource resource, Action action) {

        // Fault tolerance: reject invalid inputs
        if (user == null) {
            AccessDecision decision = new AccessDecision(false, "Access denied: user is null/unknown.");
            logs.add(new AccessLogEntry("unknown", null, resource, action, false));
            return decision;
        }
        if (resource == null) {
            AccessDecision decision = new AccessDecision(false, "Access denied: resource is null/unknown.");
            logs.add(new AccessLogEntry(user.getUserId(), user.getRole(), null, action, false));
            return decision;
        }
        if (action == null) {
            AccessDecision decision = new AccessDecision(false, "Access denied: action is null/unknown.");
            logs.add(new AccessLogEntry(user.getUserId(), user.getRole(), resource, null, false));
            return decision;
        }

        Role role = user.getRole();

        // ADMIN rules
        if (role == Role.ADMIN) {
            // Example restriction: ADMIN cannot WRITE lecture materials
            boolean allowed = !(resource == Resource.LECTURE_MATERIALS && action == Action.WRITE);

            AccessDecision decision = allowed
                    ? new AccessDecision(true, "Access granted: ADMIN permission.")
                    : new AccessDecision(false, "Access denied: ADMIN cannot WRITE LECTURE_MATERIALS.");

            logs.add(new AccessLogEntry(user.getUserId(), role, resource, action, allowed));
            return decision;
        }

        // STAFF rules
        if (role == Role.STAFF) {
            boolean allowed;
            String msg;

            switch (resource) {
                case LIBRARY_BOOKS:
                    allowed = (action == Action.READ);
                    msg = allowed
                            ? "Access granted: STAFF can READ LIBRARY_BOOKS."
                            : "Access denied: STAFF cannot WRITE LIBRARY_BOOKS.";
                    break;

                case LECTURE_MATERIALS:
                    // Staff can READ and WRITE lecture materials
                    allowed = true;
                    msg = "Access granted: STAFF can " + action + " LECTURE_MATERIALS.";
                    break;

                case EXAM_PAPERS:
                    // Example: staff can READ exam papers but not WRITE (adjust if spec differs)
                    allowed = (action == Action.READ);
                    msg = allowed
                            ? "Access granted: STAFF can READ EXAM_PAPERS."
                            : "Access denied: STAFF cannot WRITE EXAM_PAPERS.";
                    break;

                default:
                    allowed = false;
                    msg = "Access denied: STAFF unknown resource.";
            }

            logs.add(new AccessLogEntry(user.getUserId(), role, resource, action, allowed));
            return new AccessDecision(allowed, msg);
        }

        // STUDENT rules
        if (role == Role.STUDENT) {
            boolean allowed;
            String msg;

            switch (resource) {
                case LIBRARY_BOOKS:
                    // Student can READ library books only
                    allowed = (action == Action.READ);
                    msg = allowed
                            ? "Access granted: STUDENT can READ LIBRARY_BOOKS."
                            : "Access denied: STUDENT cannot WRITE LIBRARY_BOOKS.";
                    break;

                case LECTURE_MATERIALS:
                    // Student can READ lecture materials only
                    allowed = (action == Action.READ);
                    msg = allowed
                            ? "Access granted: STUDENT can READ LECTURE_MATERIALS."
                            : "Access denied: STUDENT cannot WRITE LECTURE_MATERIALS.";
                    break;

                case EXAM_PAPERS:
                    // Student cannot access exam papers (example)
                    allowed = false;
                    msg = "Access denied: STUDENT cannot access EXAM_PAPERS.";
                    break;

                default:
                    allowed = false;
                    msg = "Access denied: STUDENT unknown resource.";
            }

            logs.add(new AccessLogEntry(user.getUserId(), role, resource, action, allowed));
            return new AccessDecision(allowed, msg);
        }

        // Unknown role
        logs.add(new AccessLogEntry(user.getUserId(), role, resource, action, false));
        return new AccessDecision(false, "Access denied: unknown role.");
    }

    /** Read-only view of logs */
    public List<AccessLogEntry> getLogs() {
        return Collections.unmodifiableList(logs);
    }
}