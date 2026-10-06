package sort.comparator;

import student.Student;

import java.util.Comparator;

public class StudentIdComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getStudentId().compareTo(o2.getStudentId());
    }
}