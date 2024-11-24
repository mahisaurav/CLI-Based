import java.util.ArrayList;
import java.util.List;

public class Course {
    private String code;
    private String title;
    private String professor;
    private int credits;
    private List<Student> enrolledStudents;
    private List<String> prerequisites;
    private String timings;
    private int semester; // Added semester attribute

    // Updated constructor to accept semester
    public Course(String code, String title, String professor, int credits, String prerequisites, String timings, int semester) {
        this.code = code;
        this.title = title;
        this.professor = professor;
        this.credits = credits;
        this.enrolledStudents = new ArrayList<>();
        this.prerequisites = new ArrayList<>();

        if (prerequisites != null && !prerequisites.trim().isEmpty()) {
            String[] prereqArray = prerequisites.split(",");
            for (String prereq : prereqArray) {
                this.prerequisites.add(prereq.trim());
            }
        }

        this.timings = timings;
        this.semester = semester;
    }

    public Course(String code, String title, String professor, int credits, List<String> prerequisites, String timings) {
        this(code, title, professor, credits, String.join(",", prerequisites), timings, 1); // Default semester to 1
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getProfessor() {
        return professor;
    }

    public int getCredits() {
        return credits;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public String getTimings() {
        return timings;
    }

    public int getSemester() {
        return semester;
    }

    // Setters for Professor to update course details
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    // Enrollment Methods
    public void addStudent(Student student) {
        if (!enrolledStudents.contains(student)) { // Avoid duplicate enrollments
            enrolledStudents.add(student);
        } else {
            System.out.println("Student is already enrolled in this course.");
        }
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // Display Methods
    public String displayCourseDetails() {
        return "Course Code: " + code + ", Title: " + title + ", Professor: " + professor +
                ", Credits: " + credits + ", Timings: " + timings +
                ", Semester: " + semester + ", Prerequisites: " + String.join(", ", prerequisites);
    }

    // Assign Professor
    public void assignProfessor(String professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return displayCourseDetails();
    }
}
