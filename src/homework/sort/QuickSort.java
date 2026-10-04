package homework.sort;

import homework.student.Student;
import java.util.Comparator;
import java.util.List;

public class QuickSort implements SortStrategy {

    @Override
    public void sort(List<Student> students, Comparator<Student> comparator) {
        if (students == null || students.size() < 2) return;
        quickSort(students, 0, students.size() - 1, comparator);
    }

    private void quickSort(List<Student> list, int left, int right, Comparator<Student> comparator) {
        if (left >= right) return;
        Student pivot = list.get((left + right) / 2);
        int i = left;
        int j = right;
        while (i <= j) {
            while (comparator.compare(list.get(i), pivot) < 0) i++;
            while (comparator.compare(list.get(j), pivot) > 0) j--;
            if (i <= j) {
                Student temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
                i++;
                j--;
            }
        }
        quickSort(list, left, j, comparator);
        quickSort(list, i, right, comparator);
    }
}