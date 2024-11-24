import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends Person implements User {
    private static List<Student> students = new ArrayList<>();
    private List<Course> enrolledCourses;
    private List<Grade> grades;
    private int currentSemester;

    public Student(String email, String name, String password) {
        super(email, name, password);
        this.enrolledCourses = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.currentSemester = 1;
    }

    @Override
    public void signUp(String email, String name, String password) {
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email.trim())) {
                System.out.println("Email already registered.");
                return;
            }
        }
        students.add(this);
        System.out.println("Student signed up successfully.");

        // Auto-enroll in Semester 1 courses
        List<Course> sem1Courses = Main.catalog.getCoursesBySemester(1);
        for (Course course : sem1Courses) {
            if (course.getCredits() == 2 || course.getCredits() == 4) { // Ensure credit limit criteria
                enrolledCourses.add(course);
                course.addStudent(this);
                System.out.println("Enrolled in course: " + course.getTitle());
            }
        }
    }

    @Override
    public void login(String email, String password) {
        if (this.getEmail().equalsIgnoreCase(email.trim()) && validatePassword(password)) {
            System.out.println("Student logged in successfully.");
            showMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStudent Menu:");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. View Schedule");
            System.out.println("4. Track Academic Progress");
            System.out.println("5. Drop a Course");
            System.out.println("6. Submit Complaint");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    viewAvailableCourses();
                    break;
                case 2:
                    registerForCourse(scanner);
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                    trackAcademicProgress();
                    break;
                case 5:
                    dropCourse(scanner);
                    break;
                case 6:
                    submitComplaint(scanner);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAvailableCourses() {
        List<Course> availableCourses = Main.catalog.getCoursesBySemester(currentSemester);
        if (availableCourses.isEmpty()) {
            System.out.println("No courses available for your current semester.");
            return;
        }
        System.out.println("\nAvailable Courses for Semester " + currentSemester + ":");
        for (Course course : availableCourses) {
            System.out.println(course.displayCourseDetails());
        }
    }

    private void registerForCourse(Scanner scanner) {
        System.out.println("Enter course code to register:");
        String courseCode = scanner.nextLine().trim();
        Course course = Main.catalog.getCourseByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (enrolledCourses.contains(course)) {
            System.out.println("You are already enrolled in this course.");
            return;
        }

        if (canRegister(course)) {
            enrolledCourses.add(course);
            course.addStudent(this);
            System.out.println("Successfully registered for the course: " + course.getTitle());
        } else {
            System.out.println("Cannot register for this course.");
        }
    }

    private boolean canRegister(Course course) {
        // Check if course is in the current semester
        if (currentSemester != course.getSemester()) {
            System.out.println("Course not available in your current semester.");
            return false;
        }

        // Check prerequisites
        List<String> prerequisites = course.getPrerequisites();
        if (!prerequisites.isEmpty()) {
            for (String prerequisite : prerequisites) {
                boolean prerequisiteMet = false;
                for (Grade grade : grades) {
                    if (grade.getCourseCode().equalsIgnoreCase(prerequisite.trim()) && !grade.getGrade().equalsIgnoreCase("F")) {
                        prerequisiteMet = true;
                        break;
                    }
                }
                if (!prerequisiteMet) {
                    System.out.println("Prerequisite not met for course: " + course.getTitle());
                    return false;
                }
            }
        }

        // Check credit limit
        int totalCredits = 0;
        for (Course enrolledCourse : enrolledCourses) {
            totalCredits += enrolledCourse.getCredits();
        }
        totalCredits += course.getCredits();
        if (totalCredits > 20) {
            System.out.println("Credit limit exceeded. You cannot register for more than 20 credits.");
            return false;
        }

        return true;
    }

    private int getSemesterFromCourseCode(String courseCode) {
        String numberPart = courseCode.replaceAll("[^0-9]", "");
        if (numberPart.length() >= 1) {
            try {
                int semester = Integer.parseInt(String.valueOf(numberPart.charAt(0)));
                return semester;
            } catch (NumberFormatException e) {
                // Invalid format
            }
        }
        return -1; // Invalid semester
    }

    private void viewSchedule() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }
        System.out.println("\nYour Schedule for Semester " + currentSemester + ":");
        for (Course course : enrolledCourses) {
            System.out.println(course.displayCourseDetails());
        }
    }

    private void trackAcademicProgress() {
        System.out.println("\nTracking Academic Progress...");
        if (currentSemesterComplete()) {
            if (grades.isEmpty()) {
                System.out.println("No grades available.");
                return;
            }
            System.out.println("Your Grades:");
            for (Grade grade : grades) {
                System.out.println("Course: " + grade.getCourseCode() + ", Grade: " + grade.getGrade());
            }
            System.out.println("SGPA: " + calculateSGPA());
            System.out.println("CGPA: " + calculateCGPA());
        } else {
            System.out.println("Current semester not complete. Cannot track academic progress.");
        }
    }

    private boolean currentSemesterComplete() {
        // A semester is complete if all enrolled courses have grades assigned
        for (Course course : enrolledCourses) {
            boolean gradeAssigned = false;
            for (Grade grade : grades) {
                if (grade.getCourseCode().equalsIgnoreCase(course.getCode()) && !grade.getGrade().isEmpty()) {
                    gradeAssigned = true;
                    break;
                }
            }
            if (!gradeAssigned) {
                return false;
            }
        }
        return true;
    }

    private double calculateSGPA() {
        int totalCredits = 0;
        double totalPoints = 0.0;

        for (Grade grade : grades) {
            Course course = Main.catalog.getCourseByCode(grade.getCourseCode());
            if (course != null) {
                double gradePoints = getGradePoints(grade.getGrade());
                totalCredits += course.getCredits();
                totalPoints += gradePoints * course.getCredits();
            }
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    private double calculateCGPA() {
        // Assuming there's a List of SGPAs for each semester stored in the student record
        List<Double> semesterSgpaList = getSgpasForAllSemesters(); // Retrieve the list of all semester SGPAs
        if (semesterSgpaList.isEmpty()) {
            return 0.0;
        }

        double totalSgpa = 0.0;
        for (double sgpa : semesterSgpaList) {
            totalSgpa += sgpa;
        }
        return totalSgpa / semesterSgpaList.size();
    }

    private List<Double> getSgpasForAllSemesters() {
        List<Double> sgpas = new ArrayList<>();


        sgpas.add(7.3);
        sgpas.add(8.6);
        sgpas.add(8.0);
        return sgpas;
    }

    private double getGradePoints(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return 9.0;
            case "B": return 8.0;
            case "C": return 7.0;
            case "D": return 6.0;
            case "F": return 4.0;
            default: return 0.0;
        }
    }

    private void dropCourse(Scanner scanner) {
        System.out.println("Enter course code to drop:");
        String courseCode = scanner.nextLine().trim();
        Course course = Main.catalog.getCourseByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (enrolledCourses.remove(course)) {
            course.getEnrolledStudents().remove(this);
            System.out.println("Successfully dropped the course: " + course.getTitle());
        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }

    private void submitComplaint(Scanner scanner) {
        System.out.println("Enter your complaint description:");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) {
            System.out.println("Complaint description cannot be empty.");
            return;
        }
        Complaint complaint = new Complaint(description);
        Main.complaints.add(complaint);
        System.out.println("Complaint submitted successfully.");
    }

    public void addGrade(String courseCode, String grade) {
        grades.add(new Grade(courseCode, grade));
    }

    public String getGradeForCourse(String courseCode) {
        for (Grade grade : grades) {
            if (grade.getCourseCode().equalsIgnoreCase(courseCode.trim())) {
                return grade.getGrade();
            }
        }
        return null;
    }

    public static List<Student> getStudents() {
        return students;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int semester) {
        this.currentSemester = semester;
    }

    // Helper method for input validation
    private int getValidInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    public boolean updateGrade(String courseCode, String newGrade) {
        for (Grade grade : this.grades) { // Assuming 'grades' is a list of Grade objects
            if (grade.getCourseCode().equalsIgnoreCase(courseCode)) {
                grade.setGrade(newGrade); // Update the grade
                return true; // Indicate success
            }
        }
        return false; // Indicate failure (course not found)
    }

}
