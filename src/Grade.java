public class Grade {
    int grade;
    boolean lk;
    String subject;

    Grade(int grade, boolean lk, String subject) {
        this.grade = grade;
        this.lk = lk;
        this.subject = subject;
    }

    public int getGrade() {
        return grade;
    }
}