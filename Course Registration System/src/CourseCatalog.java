import java.util.ArrayList;
import java.util.List;

public class CourseCatalog {
    private List<Course> courses;

    public CourseCatalog() {
        courses = new ArrayList<>();
        initializeCourses();
    }

    private void initializeCourses() {
        // Semester 1
        courses.add(new Course("CS101", "Introduction to Computer Science", "Kumar", 4, "", "MWF 10-11", 1));
        courses.add(new Course("MATH101", "Calculus I", "Patel", 4, "", "TTh 9-10:30", 1));
        courses.add(new Course("PHY101", "Physics I", "Verma", 4, "", "MWF 11-12", 1));
        courses.add(new Course("CHEM101", "Chemistry I", "Singh", 4, "", "TTh 10:45-12:15", 1));
        courses.add(new Course("ENG101", "English Composition", "Gupta", 3, "", "MW 1-2:30", 1));
        courses.add(new Course("HIST101", "World History", "Rao", 3, "", "TTh 1-2:30", 1));

        // Semester 2
        courses.add(new Course("CS102", "Data Structures", "Kumar", 4, "CS101", "MWF 10-11", 2));
        courses.add(new Course("MATH102", "Calculus II", "Patel", 4, "MATH101", "TTh 9-10:30", 2));
        courses.add(new Course("PHY102", "Physics II", "Verma", 4, "PHY101", "MWF 11-12", 2));
        courses.add(new Course("CHEM102", "Chemistry II", "Singh", 4, "CHEM101", "TTh 10:45-12:15", 2));
        courses.add(new Course("ENG102", "Literature", "Gupta", 3, "", "MW 1-2:30", 2));
        courses.add(new Course("BIO101", "Biology I", "Reddy", 4, "", "TTh 1-2:30", 2));

        // Semester 3
        courses.add(new Course("CS201", "Algorithms", "Kumar", 4, "CS102", "MWF 10-11", 3));
        courses.add(new Course("MATH201", "Linear Algebra", "Patel", 4, "", "TTh 9-10:30", 3));
        courses.add(new Course("CS202", "Computer Organization", "Verma", 4, "CS101", "MWF 11-12", 3));
        courses.add(new Course("BIO201", "Biology II", "Singh", 4, "BIO101", "TTh 10:45-12:15", 3));
        courses.add(new Course("ENG201", "Technical Writing", "Gupta", 3, "", "MW 1-2:30", 3));
        courses.add(new Course("SOC101", "Sociology", "Rao", 3, "", "TTh 1-2:30", 3));

        // Semester 4
        courses.add(new Course("CS301", "Software Engineering", "Kumar", 4, "CS201", "MWF 10-11", 4));
        courses.add(new Course("MATH301", "Probability and Statistics", "Patel", 4, "", "TTh 9-10:30", 4));
        courses.add(new Course("CS302", "Database Systems", "Verma", 4, "CS201", "MWF 11-12", 4));
        courses.add(new Course("MATH302", "Discrete Mathematics", "Singh", 4, "", "TTh 10:45-12:15", 4));
        courses.add(new Course("PHIL101", "Philosophy", "Gupta", 3, "", "MW 1-2:30", 4));
        courses.add(new Course("ART101", "Art History", "Reddy", 3, "", "TTh 1-2:30", 4));

        // Semester 5
        courses.add(new Course("CS401", "Operating Systems", "Kumar", 4, "CS301", "MWF 10-11", 5));
        courses.add(new Course("CS402", "Web Development", "Patel", 4, "CS301", "TTh 9-10:30", 5));
        courses.add(new Course("MATH401", "Numerical Methods", "Verma", 4, "", "MWF 11-12", 5));
        courses.add(new Course("BIO301", "Genetics", "Singh", 4, "BIO201", "TTh 10:45-12:15", 5));
        courses.add(new Course("HIST201", "Modern History", "Gupta", 3, "", "MW 1-2:30", 5));
        courses.add(new Course("POL101", "Political Science", "Reddy", 3, "", "TTh 1-2:30", 5));

        // Semester 6
        courses.add(new Course("CS501", "Computer Networks", "Kumar", 4, "CS401", "MWF 10-11", 6));
        courses.add(new Course("CS502", "Artificial Intelligence", "Patel", 4, "CS401", "TTh 9-10:30", 6));
        courses.add(new Course("CS503", "Mobile App Development", "Verma", 4, "", "MWF 11-12", 6));
        courses.add(new Course("BIO401", "Ecology", "Singh", 4, "BIO301", "TTh 10:45-12:15", 6));
        courses.add(new Course("CHEM201", "Organic Chemistry", "Gupta", 3, "", "MW 1-2:30", 6));
        courses.add(new Course("PSY101", "Psychology", "Reddy", 3, "", "TTh 1-2:30", 6));

        // Semester 7
        courses.add(new Course("CS601", "Machine Learning", "Kumar", 4, "CS502", "MWF 10-11", 7));
        courses.add(new Course("CS602", "Human-Computer Interaction", "Patel", 4, "CS502", "TTh 9-10:30", 7));
        courses.add(new Course("CS603", "Data Science", "Verma", 4, "CS502", "MWF 11-12", 7));
        courses.add(new Course("BIO501", "Microbiology", "Singh", 4, "BIO401", "TTh 10:45-12:15", 7));
        courses.add(new Course("ENV101", "Environmental Science", "Gupta", 3, "", "MW 1-2:30", 7));
        courses.add(new Course("MATH501", "Complex Analysis", "Reddy", 3, "", "TTh 1-2:30", 7));

        // Semester 8
        courses.add(new Course("CS701", "Capstone Project", "Kumar", 4, "CS601", "MWF 10-11", 8));
        courses.add(new Course("CS702", "Distributed Systems", "Patel", 4, "CS601", "TTh 9-10:30", 8));
        courses.add(new Course("CS703", "Cloud Computing", "Verma", 4, "CS601", "MWF 11-12", 8));
        courses.add(new Course("MATH601", "Graph Theory", "Singh", 4, "", "TTh 10:45-12:15", 8));
        courses.add(new Course("BIO601", "Biochemistry", "Gupta", 3, "", "MW 1-2:30", 8));
        courses.add(new Course("CULT101", "Cultural Studies", "Reddy", 3, "", "TTh 1-2:30", 8));
    }



    public void displayAvailableCourses(int semester) {
        System.out.println("Available Courses for Semester " + semester + ":");
        List<Course> semesterCourses = getCoursesBySemester(semester);
        if (semesterCourses.isEmpty()) {
            System.out.println("No courses available for this semester.");
            return;
        }
        for (Course course : semesterCourses) {
            System.out.println(course.displayCourseDetails() + ", Prerequisites: " + String.join(", ", course.getPrerequisites()) + ", Timings: " + course.getTimings());
        }
    }

    public Course getCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code.trim())) {
                return course;
            }
        }
        return null;
    }

    public void assignProfessorToCourse(String courseCode, String professor) {
        Course course = getCourseByCode(courseCode);
        if (course != null) {
            course.assignProfessor(professor);
            System.out.println("Professor assigned successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public List<Course> getCoursesByProfessor(String professorName) {
        List<Course> assignedCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getProfessor().equalsIgnoreCase(professorName.trim())) {
                assignedCourses.add(course);
            }
        }
        return assignedCourses;
    }

    public void addCourse(Course course) {
        if (getCourseByCode(course.getCode()) == null) {
            courses.add(course);
            System.out.println("Course added successfully.");
        } else {
            System.out.println("Course with this code already exists.");
        }
    }

    public void removeCourse(String courseCode) {
        if (courses.removeIf(course -> course.getCode().equalsIgnoreCase(courseCode.trim()))) {
            System.out.println("Course removed successfully.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void displayAvailableCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course.displayCourseDetails());
        }
    }

    public List<Course> getCoursesBySemester(int semester) {
        List<Course> semesterCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getSemester() == semester) {
                semesterCourses.add(course);
            }
        }
        return semesterCourses;
    }
}




