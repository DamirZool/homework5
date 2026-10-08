package menu;

import sort.enums.SortField;
import sort.EvenOnlySorter;
import sort.SortStrategy;
import student.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StudentService {
    /*
    Создаём переменную с типом SortStrategy, присвоенным объектом сможет быть любая из стратегий
     */
    private SortStrategy sorter;
    private final List<Student> students = new ArrayList<>();

    /*
        Конструктор, с проверкой на Null
     */
    public StudentService(SortStrategy sorter) {
        this.sorter = Objects.requireNonNull(sorter, "sorter");
    }

    /*
        Сеттер, с проверкой на Null
     */
    public void setSorter(SortStrategy sorter) {
        this.sorter = Objects.requireNonNull(sorter, "sorter");
    }

    /*

     */
    public void sortEvenOnly(SortStrategy strategy) {
        EvenOnlySorter.sortEvenOnly(students, strategy);
    }

    /*

     */
    public void setStudents(List<Student> students) {
        Objects.requireNonNull(students, "students");
        this.students.clear();
        for (Student student : students) {
            this.students.add(Objects.requireNonNull(student, "student"));
        }
    }

    /*

     */
    public boolean isEmpty() {
        return students.isEmpty();
    }

    /*

     */
    public int size() {
        return students.size();
    }

    /*

     */
    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    /*

     */
    public void clear() {
        students.clear();
    }

    /*

     */
    public void sort(int fieldCode) {
        if (isEmpty()) return;
        SortField field = SortField.fromCode(fieldCode);
        sorter.sort(students, field.getComparator());
    }

    /*

     */
    public long countOccurrences(Object target, int fieldCode, int threads) {
        return ParallelCounter.countOccurrences(students, target, fieldCode, threads);
    }
}