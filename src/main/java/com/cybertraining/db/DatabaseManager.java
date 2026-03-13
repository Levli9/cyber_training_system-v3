package com.cybertraining.db;

import com.cybertraining.model.*;

import java.util.*;

public class DatabaseManager {

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private Map<Integer, List<Question>> questions = new HashMap<>();

    public DatabaseManager() {
        seedUsers();
        seedCourses();
        seedQuestions();
    }

    private void seedUsers() {

        users.add(new User(
                1,
                "manager",
                "manager123",
                "מנהל מערכת",
                "manager",
                "הנהלה"
        ));

        users.add(new User(
                2,
                "employee",
                "1234",
                "עובד לדוגמה",
                "employee",
                "IT"
        ));
    }

    private void seedCourses() {

        Course course = new Course(
                1,
                "יסודות אבטחת מידע",
                "קורס בסיסי המלמד את עקרונות אבטחת המידע בארגון."
        );

        courses.add(course);
    }

    private void seedQuestions() {

        List<Question> list = new ArrayList<>();

        list.add(new Question(
                "מהו Phishing?",
                new String[]{
                        "ניסיון לגנוב מידע באמצעות התחזות",
                        "מערכת גיבוי נתונים",
                        "סוג של אנטי וירוס",
                        "פרוטוקול תקשורת"
                },
                0
        ));

        list.add(new Question(
                "מהי סיסמה חזקה?",
                new String[]{
                        "123456",
                        "שם פרטי",
                        "שילוב אותיות, מספרים וסימנים",
                        "תאריך לידה"
                },
                2
        ));

        questions.put(1, list);
    }

    public User authenticate(String username, String password) {

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                return user;
            }
        }

        return null;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Question> getQuestionsForCourse(int courseId) {
        return questions.get(courseId);
    }

    public void createTables() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTables'");
    }
}