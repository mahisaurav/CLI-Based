import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static CourseCatalog catalog = new CourseCatalog();
    public static List<Complaint> complaints = new ArrayList<>();
    public static List<Student> students = new ArrayList<>();
    public static List<Professor> professors = new ArrayList<>();
    public static Admin admin = new Admin("admin@example.com", "Admin");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // random data
        Professor professor1 = new Professor("Prof1@iiitd.com", "Sanjit", "password123");
        Professor professor2 = new Professor("Prof2@iiitd.com", "Tamaam", "password222");
        professors.add(professor1);
        professors.add(professor2);

        Course course1 = new Course("CS101", "Intro to Computer Science", "Sonal", 4, new ArrayList<>(), "MWF 10-11");
        Course course2 = new Course("CS102", "Data Structures", "Preeti", 4, new ArrayList<>(), "TTh 1-2:30");

        // Assign courses to professors
        professor1.assignCourse(course1);
        professor1.assignCourse(course2);
        professor2.assignCourse(course1);

        // Add professors to the list
        professors.add(professor1);
        professors.add(professor2);

        // Random Students
        Student student1 = new Student("student1@iiitd.com", "rishabh", "passwordA");
        Student student2 = new Student("student2@iiitd.com", "Saurav", "passwordB");
        students.add(student1);
        students.add(student2);

        while (true) {
            System.out.println("\nWelcome to Course Registration System");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    signUp(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void signUp(Scanner scanner) {
        System.out.println("\nSign Up as:");
        System.out.println("1. Student");
        System.out.println("2. Professor");
        System.out.print("Enter your choice: ");
        int choice = getValidInt(scanner);

        switch (choice) {
            case 1:
                signUpStudent(scanner);
                break;
            case 2:
                signUpProfessor(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void signUpStudent(Scanner scanner) {
        System.out.println("\nSign up as Student:");
        System.out.print("Email: ");
        String studentEmail = scanner.nextLine().trim();
        System.out.print("Name: ");
        String studentName = scanner.nextLine().trim();
        System.out.print("Password: ");
        String studentPassword = scanner.nextLine().trim();

        Student student = new Student(studentEmail, studentName, studentPassword);
        student.signUp(studentEmail, studentName, studentPassword);
        students.add(student);
    }

    private static void signUpProfessor(Scanner scanner) {
        System.out.println("\nSign up as Professor:");
        System.out.print("Email: ");
        String professorEmail = scanner.nextLine().trim();
        System.out.print("Name: ");
        String professorName = scanner.nextLine().trim();
        System.out.print("Password: ");
        String professorPassword = scanner.nextLine().trim();

        Professor professor = new Professor(professorEmail, professorName, professorPassword);
        professor.signUp(professorEmail, professorName, professorPassword);
        professors.add(professor);
    }

    private static void login(Scanner scanner) {
        System.out.println("\nLogin as:");
        System.out.println("1. Admin");
        System.out.println("2. Student");
        System.out.println("3. Professor");
        System.out.print("Enter your choice: ");
        int choice = getValidInt(scanner);

        switch (choice) {
            case 1:
                loginAdmin(scanner);
                break;
            case 2:
                loginStudent(scanner);
                break;
            case 3:
                loginProfessor(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void loginAdmin(Scanner scanner) {
        System.out.println("\nLogin as Admin:");
        System.out.print("Email: ");
        String adminEmail = scanner.nextLine().trim();
        System.out.print("Password: ");
        String adminPassword = scanner.nextLine().trim();

        admin.login(adminEmail, adminPassword);
    }

    private static void loginStudent(Scanner scanner) {
        System.out.println("\nLogin as Student:");
        System.out.print("Email: ");
        String studentEmail = scanner.nextLine().trim();
        System.out.print("Password: ");
        String studentPassword = scanner.nextLine().trim();

        boolean studentFound = false;
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(studentEmail)) {
                student.login(studentEmail, studentPassword);
                studentFound = true;
                break;
            }
        }

        if (!studentFound) {
            System.out.println("Student not found.");
        }
    }

    private static void loginProfessor(Scanner scanner) {
        System.out.println("\nLogin as Professor:");
        System.out.print("Email: ");
        String professorEmail = scanner.nextLine().trim();
        System.out.print("Password: ");
        String professorPassword = scanner.nextLine().trim();

        boolean professorFound = false;
        for (Professor professor : professors) {
            if (professor.getEmail().equalsIgnoreCase(professorEmail)) {
                professor.login(professorEmail, professorPassword);
                professorFound = true;
                break;
            }
        }

        if (!professorFound) {
            System.out.println("Professor not found.");
        }
    }
    private static int getValidInt(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
