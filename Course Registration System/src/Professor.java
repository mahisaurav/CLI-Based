import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Professor extends Person implements User {
    private List<Course> assignedCourses;

    public Professor(String email, String name, String password) {
        super(email, name, password);
        this.assignedCourses = new ArrayList<>();
    }

    @Override
    public void signUp(String email, String name, String password) {
        for (Professor existingProfessor : Main.professors) {
            if (existingProfessor.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Professor with this email already exists.");
                return;
            }
        }
        Professor newProfessor = new Professor(email, name, password);
        Main.professors.add(newProfessor);
        System.out.println("Professor " + name + " has been signed up successfully.");
    }


    @Override
    public void login(String email, String password) {
        if (this.getEmail().equalsIgnoreCase(email.trim()) && validatePassword(password)) {
            System.out.println("Professor logged in successfully.");
            showMenu();
        } else {
            System.out.println("Invalid Data.");
        }
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nProfessor Menu:");
            System.out.println("1. View Assigned Courses");
            System.out.println("2. View Enrolled Students");
            System.out.println("3. Update Course Details");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    viewAssignedCourses();
                    break;
                case 2:
                    viewEnrolledStudents(scanner);
                    break;
                case 3:
                    updateCourseDetails(scanner);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAssignedCourses() {
        if (assignedCourses.isEmpty()) {
            System.out.println("No courses assigned to you.");
            return;
        }
        System.out.println("\nAssigned Courses:");
        for (Course course : assignedCourses) {
            System.out.println(course.displayCourseDetails());
        }
    }

    private void viewEnrolledStudents(Scanner scanner) {
        System.out.println("Enter course code to view enrolled students:");
        String code = scanner.nextLine().trim();
        Course course = Main.catalog.getCourseByCode(code);

        if (course != null && course.getProfessor().equalsIgnoreCase(this.getName())) {
            List<Student> enrolledStudents = course.getEnrolledStudents();
            if (enrolledStudents.isEmpty()) {
                System.out.println("No students enrolled in this course.");
                return;
            }
            System.out.println("\nEnrolled Students in " + course.getTitle() + ":");
            for (Student student : enrolledStudents) {
                String grade = student.getGradeForCourse(course.getCode());
                System.out.println("Name: " + student.getName() + ", Email: " + student.getEmail() + ", Grade: " + (grade != null ? grade : "N/A"));
            }
        } else {
            System.out.println("Course not found or not assigned to you.");
        }
    }

    public void assignCourse(Course course) {
        assignedCourses.add(course);
    }

    private void updateCourseDetails(Scanner scanner) {
        System.out.println("Enter course code to update:");
        String courseCode = scanner.nextLine().trim();
        Course course = Main.catalog.getCourseByCode(courseCode);

        if (course != null && course.getProfessor().equalsIgnoreCase(this.getName())) {
            System.out.println("Updating details for course: " + course.getTitle());

            System.out.print("Enter new course title (leave blank to keep current): ");
            String newTitle = scanner.nextLine().trim();
            if (!newTitle.isEmpty()) {
                course.setTitle(newTitle);
            }

            System.out.print("Enter new number of credits (2 or 4, enter 0 to be current): ");
            int newCredits = getValidInt(scanner);
            if (newCredits == 2 || newCredits == 4) {
                course.setCredits(newCredits);
            } else if (newCredits != 0) {
                System.out.println("Invalid credit value.");
            }

            System.out.print("Enter new timings : ");
            String newTimings = scanner.nextLine().trim();
            if (!newTimings.isEmpty()) {
                course.setTimings(newTimings);
            }

            System.out.print("Enter new prerequisites (comma separated, or 'none'): ");
            String newPrerequisites = scanner.nextLine().trim();
            if (!newPrerequisites.isEmpty()) {
                if (!newPrerequisites.equalsIgnoreCase("none")) {
                    List<String> prereqList = List.of(newPrerequisites.split(","));
                    course.setPrerequisites(prereqList);
                } else {
                    course.setPrerequisites(new ArrayList<>());
                }
            }

            System.out.println("Course details updated successfully.");
        } else {
            System.out.println("Course not found or not assigned to you.");
        }
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
