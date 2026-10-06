package sort;

import student.Student;

import java.util.Comparator;
import java.util.List;

public class InsertionSort implements SortStrategy{
    @Override
    public void sort(List<Student> students, Comparator<Student> comparator) {
        long t1 = System.nanoTime();
        for (int i = 1; i < students.size(); i++) {
            Student current = students.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(students.get(j), current) > 0) {
                students.set(j+1, students.get(j));
                --j;
            }
            students.set(j+1, current);
        }
        System.out.printf("Потрачено на сортировку: %d%n", (System.nanoTime() - t1));
    }
}