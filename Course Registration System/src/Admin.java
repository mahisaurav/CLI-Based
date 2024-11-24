import java.util.List;
import java.util.Scanner;

public class Admin extends Person implements User {
    private static final String FIXED_PASSWORD = "admin123";

    public Admin(String email, String name) {
        super(email, name, FIXED_PASSWORD);
    }

    @Override
    public void signUp(String email, String name, String password) {
        System.out.println("Admin account already exists.");
    }

    @Override
    public void login(String email, String password) {
        if (this.getEmail().equalsIgnoreCase(email.trim()) && validatePassword(password)) {
            System.out.println("Admin logged in successfully.");
            manageMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void manageMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Course Catalog");
            System.out.println("2. Manage Student Records");
            System.out.println("3. Assign Professors to Courses");
            System.out.println("4. Handle Complaints");
            System.out.println("5. Update Semester Courses");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    manageCourseCatalog(scanner);
                    break;
                case 2:
                    manageStudentRecords(scanner);
                    break;
                case 3:
                    assignProfessorsToCourses(scanner);
                    break;
                case 4:
                    handleComplaints(scanner);
                    break;
                case 5:
                    updateSemesterCourses(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void manageCourseCatalog(Scanner scanner) {
        while (true) {
            System.out.println("\nManage Course Catalog:");
            System.out.println("1. View Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Remove Course");
            System.out.println("4. View Courses by Professor");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    Main.catalog.displayAvailableCourses();
                    break;
                case 2:
                    addCourse(scanner);
                    break;
                case 3:
                    removeCourse(scanner);
                    break;
                case 4:
                    viewCoursesByProfessor(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addCourse(Scanner scanner) {
        System.out.println("\nAdd New Course:");
        System.out.print("Enter course code: ");
        String code = scanner.nextLine().trim();

        if (Main.catalog.getCourseByCode(code) != null) {
            System.out.println("Course with this code already exists.");
            return;
        }

        System.out.print("Enter course title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter professor name: ");
        String professor = scanner.nextLine().trim();

        System.out.print("Enter number of credits (2 or 4): ");
        int credits = getValidInt(scanner);
        if (credits != 2 && credits != 4) {
            System.out.println("Invalid number of credits. Must be 2 or 4.");
            return;
        }

        System.out.print("Enter prerequisites (comma separated, or leave blank for none): ");
        String prerequisites = scanner.nextLine().trim();
        if (prerequisites.isEmpty()) {
            prerequisites = "none";
        }

        System.out.print("Enter timings: ");
        String timings = scanner.nextLine().trim();

        System.out.print("Enter semester number (1-8): ");
        int semester = getValidInt(scanner);
        if (semester < 1 || semester > 8) {
            System.out.println("Invalid semester number. Must be between 1 and 8.");
            return;
        }

        Course newCourse = new Course(code, title, professor, credits, prerequisites, timings, semester);
        Main.catalog.addCourse(newCourse);
    }

    private void removeCourse(Scanner scanner) {
        System.out.println("\nRemove Course:");
        System.out.print("Enter course code to remove: ");
        String code = scanner.nextLine().trim();
        Main.catalog.removeCourse(code);
    }

    private void viewCoursesByProfessor(Scanner scanner) {
        System.out.println("\nView Courses by Professor:");
        System.out.print("Enter professor name: ");
        String professorName = scanner.nextLine().trim();
        List<Course> coursesByProfessor = Main.catalog.getCoursesByProfessor(professorName);
        if (coursesByProfessor.isEmpty()) {
            System.out.println("No courses found for professor: " + professorName);
            return;
        }
        System.out.println("Courses taught by " + professorName + ":");
        for (Course course : coursesByProfessor) {
            System.out.println(course.displayCourseDetails());
        }
    }

    public void manageStudentRecords(Scanner scanner) {
        System.out.println("\nManage Student Records:");
        if (Main.students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }
        for (Student student : Main.students) {
            System.out.println("Email: " + student.getEmail() + ", Name: " + student.getName() + ", Current Semester: " + student.getCurrentSemester());
        }
    }

    public void assignProfessorsToCourses(Scanner scanner) {
        System.out.println("\nAssign Professors to Courses:");
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine().trim();
        System.out.print("Enter professor name: ");
        String professorName = scanner.nextLine().trim();
        Main.catalog.assignProfessorToCourse(courseCode, professorName);
    }

    public void handleComplaints(Scanner scanner) {
        System.out.println("\nHandle Complaints:");
        if (Main.complaints.isEmpty()) {
            System.out.println("No complaints to handle.");
            return;
        }
        for (int i = 0; i < Main.complaints.size(); i++) {
            Complaint complaint = Main.complaints.get(i);
            System.out.println((i + 1) + ". Description: " + complaint.getDescription() + ", Status: " + complaint.getStatus());
        }
        System.out.print("Enter complaint number to resolve (0 to cancel): ");

        int index = getValidInt(scanner) - 1;
        if (index >= 0 && index < Main.complaints.size()) {
            Main.complaints.get(index).resolveComplaint();
            System.out.println("Complaint resolved successfully.");
        } else if (index != -1) {
            System.out.println("Invalid complaint number.");
        }
    }

    public void updateSemesterCourses(Scanner scanner) {
        System.out.println("\nUpdate Semester Courses:");
        System.out.print("Enter semester number (1-8): ");
        int semester = getValidInt(scanner);
        if (semester < 1 || semester > 8) {
            System.out.println("Invalid semester number. Must be between 1 and 8.");
            return;
        }
        Main.catalog.displayAvailableCourses(semester);
    }
    private int getValidInt(Scanner scanner) {
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
