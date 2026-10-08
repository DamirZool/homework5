package student;

public class Student {
    public static final double MAX_SCORE = 100;
    public static final double MIN_SCORE = 0;

    private final String groupNum;
    private final double avgScore;
    private final String studentId;

    public Student(String groupNum, double avgScore, String studentId) {
        this.groupNum = groupNum;
        this.avgScore = avgScore;
        this.studentId = studentId;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public static class Builder {
        private String groupNum;
        private double avgScore;
        private String studentId;

        public Builder setGroupNum(String groupNum) {
            this.groupNum = groupNum;
            if (groupNum == null || groupNum.isEmpty())
                throw new IllegalArgumentException("Группа не может быть пустой");
            return this;
        }

        public Builder setAvgScore(double avgScore) {
            this.avgScore = avgScore;
            if (avgScore < MIN_SCORE || avgScore > MAX_SCORE)
                throw new IllegalArgumentException("Средний балл вне диапазона");
            return this;
        }

        public Builder setStudentId(String studentId) {
            this.studentId = studentId;
            if (studentId == null || studentId.isEmpty())
                throw new IllegalArgumentException("Номер зачётной книжки не должен быть пустым");
            return this;
        }

        public Student build() {
            return new Student(groupNum, avgScore, studentId);
        }
    }

    @Override
    public String toString() {
        return String.format("Студент с зачётной книжкой номер %s из группы %s, имеет средний бал %.2f", studentId, groupNum, avgScore);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }
}
