package accesscontrol;

public class Main {

    public static void main(String[] args) {

        AccessControlPolicyEngine engine = new AccessControlPolicyEngine();

        // Create users
        User admin = new User("user1", Role.ADMIN);
        User staff = new User("user2", Role.STAFF);
        User student = new User("user3", Role.STUDENT);

        System.out.println("=== ADMIN TESTS ===");
        System.out.println(engine.evaluateAccess(admin, Resource.LIBRARY_BOOKS, Action.READ));
        System.out.println(engine.evaluateAccess(admin, Resource.LECTURE_MATERIALS, Action.WRITE));
        System.out.println(engine.evaluateAccess(admin, Resource.EXAM_PAPERS, Action.READ));

        System.out.println("\n=== STAFF TESTS ===");
        System.out.println(engine.evaluateAccess(staff, Resource.LIBRARY_BOOKS, Action.READ));
        System.out.println(engine.evaluateAccess(staff, Resource.LIBRARY_BOOKS, Action.WRITE));
        System.out.println(engine.evaluateAccess(staff, Resource.LECTURE_MATERIALS, Action.WRITE));
        System.out.println(engine.evaluateAccess(staff, Resource.EXAM_PAPERS, Action.READ));

        System.out.println("\n=== STUDENT TESTS ===");
        System.out.println(engine.evaluateAccess(student, Resource.LIBRARY_BOOKS, Action.READ));
        System.out.println(engine.evaluateAccess(student, Resource.LECTURE_MATERIALS, Action.WRITE));
        System.out.println(engine.evaluateAccess(student, Resource.EXAM_PAPERS, Action.READ));

        System.out.println("\n=== FAULT TOLERANCE TESTS ===");
        System.out.println(engine.evaluateAccess(null, Resource.LIBRARY_BOOKS, Action.READ));
        System.out.println(engine.evaluateAccess(student, null, Action.READ));
        System.out.println(engine.evaluateAccess(student, Resource.LIBRARY_BOOKS, null));

        System.out.println("\n=== ACCESS LOG ===");

        for (AccessLogEntry entry : engine.getLogs()) {
            System.out.println(entry);
        }

            }
        }