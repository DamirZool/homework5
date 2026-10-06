package tests.sort;

import sort.QuickSort;
import sort.SortStrategy;
import sort.comparator.AvgScoreComparator;
import sort.comparator.GroupNumComparator;
import sort.comparator.StudentIdComparator;
import student.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestQuick {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testEmpty();
        testSingle();
        testAlreadySorted();
        testReverse();
        testDuplicates();
        testByAvgScore();
        testByGroupNum();
        testByStudentId();

        System.out.println("Пройдено: " + passed);
        System.out.println("Упало: " + failed);
    }

    private static void testEmpty() {
        List<Student> list = new ArrayList<>();
        sort(list, new AvgScoreComparator());
        check("testEmpty", 0, list.size());
    }

    private static void testSingle() {
        List<Student> list = listOf(s("A", 4.5, "1"));
        sort(list, new AvgScoreComparator());
        check("testSingle", 1, list.size());
        if (!list.get(0).getStudentId().equals("1")) fail("testSingle: элемент изменился");
        else passed++;
    }

    private static void testAlreadySorted() {
        List<Student> list = listOf(
                s("A", 3.0, "1"),
                s("B", 4.0, "2"),
                s("C", 5.0, "3")
        );
        sort(list, new AvgScoreComparator());
        if (isSortedByAvgScore(list)) passed++;
        else fail("testAlreadySorted");
    }

    private static void testReverse() {
        List<Student> list = listOf(
                s("C", 5.0, "3"),
                s("B", 4.0, "2"),
                s("A", 3.0, "1")
        );
        sort(list, new AvgScoreComparator());
        if (isSortedByAvgScore(list)) passed++;
        else fail("testReverse");
    }

    private static void testDuplicates() {
        List<Student> list = listOf(
                s("A", 4.0, "1"),
                s("B", 3.0, "2"),
                s("C", 4.0, "3"),
                s("D", 3.0, "4"),
                s("E", 5.0, "5")
        );
        sort(list, new AvgScoreComparator());
        if (isSortedByAvgScore(list)) passed++;
        else fail("testDuplicates");
    }

    private static void testByAvgScore() {
        List<Student> list = listOf(
                s("A", 4.5, "1"),
                s("B", 3.2, "2"),
                s("C", 5.0, "3")
        );
        sort(list, new AvgScoreComparator());
        if (isSortedByAvgScore(list)) passed++;
        else fail("testByAvgScore");
    }

    private static void testByGroupNum() {
        List<Student> list = listOf(
                s("В", 4.5, "1"),
                s("А", 3.2, "2"),
                s("Б", 5.0, "3")
        );
        sort(list, new GroupNumComparator());
        if (isSortedByGroupNum(list)) passed++;
        else fail("testByGroupNum");
    }

    private static void testByStudentId() {
        List<Student> list = listOf(
                s("A", 4.5, "300"),
                s("B", 3.2, "100"),
                s("C", 5.0, "200")
        );
        sort(list, new StudentIdComparator());
        if (isSortedByStudentId(list)) passed++;
        else fail("testByStudentId");
    }

    private static void sort(List<Student> list, Comparator<Student> comparator) {
        SortStrategy strategy = new QuickSort();
        strategy.sort(list, comparator);
    }

    private static boolean isSortedByAvgScore(List<Student> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).getAvgScore() > list.get(i).getAvgScore()) return false;
        }
        return true;
    }

    private static boolean isSortedByGroupNum(List<Student> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).getGroupNum().compareTo(list.get(i).getGroupNum()) > 0) return false;
        }
        return true;
    }

    private static boolean isSortedByStudentId(List<Student> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).getStudentId().compareTo(list.get(i).getStudentId()) > 0) return false;
        }
        return true;
    }

    private static Student s(String group, double avg, String id) {
        return new Student.Builder()
                .setGroupNum(group)
                .setAvgScore(avg)
                .setStudentId(id)
                .build();
    }

    private static List<Student> listOf(Student... students) {
        List<Student> list = new ArrayList<>();
        for (Student st : students) list.add(st);
        return list;
    }

    private static void check(String name, int expected, int actual) {
        if (expected == actual) passed++;
        else fail(name + ": ожидалось " + expected + ", получено " + actual);
    }

    private static void fail(String msg) {
        failed++;
        System.out.println("FAIL " + msg);
    }
}