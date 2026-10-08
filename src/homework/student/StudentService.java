package student;

import parallel.ParallelCounter;
import sort.enums.SortField;
import sort.EvenOnlySorter;
import sort.SortStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StudentService {
    private SortStrategy sorter;
    private final List<Student> students = new ArrayList<>();

    public StudentService(SortStrategy sorter) {
        this.sorter = Objects.requireNonNull(sorter, "StudentService");
    }

    public void setSorter(SortStrategy sorter) {
        this.sorter = Objects.requireNonNull(sorter, "StudentService.setSorter");
    }

    public void sortEvenOnly() {
        EvenOnlySorter.sortEvenOnly(students, sorter);
    }

    public void setStudents(List<Student> students) {
        Objects.requireNonNull(students, "StudentService.setStudents");
        this.students.clear();
        for (Student student : students) {
            this.students.add(Objects.requireNonNull(student, "StudentService.setStudents"));
        }
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    public int size() {
        return students.size();
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public void clear() {
        students.clear();
    }

    public void sort(int fieldCode) {
        if (isEmpty()) return;
        SortField field = SortField.fromCode(fieldCode);
        sorter.sort(students, field.getComparator());
    }

    public long countOccurrences(Object target, int fieldCode, int threads) {
        return ParallelCounter.countOccurrences(students, target, fieldCode, threads);
    }
}