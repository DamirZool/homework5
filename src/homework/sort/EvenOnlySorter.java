package sort;

import sort.comparator.AvgScoreComparator;
import student.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class EvenOnlySorter {

    private EvenOnlySorter() {}

    public static void sortEvenOnly(List<Student> students, SortStrategy strategy) {
        if (students == null || students.size() < 2) return;

        //Сбор чётных
        //Получим второй список с позициями
        List<Integer> evenIndexes = new ArrayList<>();
        List<Student> evenStudents = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (isEven(s)) {
                evenIndexes.add(i);
                evenStudents.add(s);
            }
        }

        // Только для среднего балла
        Comparator<Student> byAvgScore = new AvgScoreComparator();
        strategy.sort(evenStudents, byAvgScore);

        //Сортируем только новый список
        for (int k = 0; k < evenIndexes.size(); k++) {
            students.set(evenIndexes.get(k), evenStudents.get(k));
        }
    }
    // Помещаем отсортированные на места
    // 4.9 -> будет чётная, помнить!
    private static boolean isEven(Student s) {
        return ((int) s.getAvgScore()) % 2 == 0;
    }
}