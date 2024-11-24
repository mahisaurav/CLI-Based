public class Grade {
    private String courseCode;
    private String grade;

    public Grade(String courseCode, String grade) {
        this.courseCode = courseCode;
        this.grade = grade;
    }

    public String getCourseCode() {

        return courseCode;
    }

    public String getGrade() {

        return grade;
    }

    public String setGrade(String newGrade) {
        return grade;
    }
}
