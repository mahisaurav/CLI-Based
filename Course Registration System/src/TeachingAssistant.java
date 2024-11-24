import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeachingAssistant extends Student {


    private Professor assignedProfessor;
    private Course course;

    public TeachingAssistant(String email, String name, String password, Professor assignedProfessor) {
        super(email, name, password);
        this.assignedProfessor = assignedProfessor;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nTeaching Assistant Menu:");
            System.out.println("1. View Assigned Professor");
            System.out.println("2. Update Student Grades");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Assigned Professor: " + assignedProfessor.getName());
                    break;
                case 2:
                    updateStudentGrades(scanner);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateStudentGrades(Scanner scanner) {
        System.out.println("Enter Student Email:");
        String studentEmail = scanner.nextLine().trim();
        Student foundStudent = null;

        // Find the student by email
        for (Student student : Main.students) {
            if (student.getEmail().equalsIgnoreCase(studentEmail)) {
                foundStudent = student;
                break;
            }
        }

        // If student is found, proceed to update the grade
        if (foundStudent != null) {
            System.out.print("Enter Course Code: ");
            String courseCode = scanner.nextLine().trim();

            System.out.print("Enter new grade: ");
            String newGrade = scanner.nextLine().trim();

            boolean success = foundStudent.updateGrade(courseCode, newGrade);

            if (success) {
                System.out.println("Grade updated for " + foundStudent.getName() + " in course " + courseCode + ".");
            } else {
                System.out.println("Failed to update grade. Please check if the course code is correct.");
            }
        } else {
            System.out.println("Student not found.");
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
