package sort.comparator;

import student.Student;

import java.util.Comparator;

public class AvgScoreComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return Double.compare(o1.getAvgScore(), o2.getAvgScore());
    }
}
