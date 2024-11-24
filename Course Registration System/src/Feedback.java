import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feedback<T> {
    private T rating;
    private String comment;

    public Feedback(T rating, String comment) {
        if (rating instanceof Integer && ((Integer) rating < 1 || (Integer) rating > 5)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
        this.comment = comment;
    }

    public T getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public static class CourseFeedback {
        private String courseCode;
        private List<Feedback<?>> feedbackList;

        public CourseFeedback(String courseCode) {
            this.courseCode = courseCode;
            this.feedbackList = new ArrayList<>();
        }

        public void addFeedback(Feedback<?> feedback) {
            feedbackList.add(feedback);
        }

        public List<Feedback<?>> getFeedbackList() {
            return feedbackList;
        }

        public String getCourseCode() {
            return courseCode;
        }
    }
}
