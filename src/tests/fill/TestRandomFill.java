package tests.fill;

import homework.student.Student;
import homework.fill.RandomFill;

import java.util.List;

public class TestRandomFill {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testSizeOfFill();
        testGroupNumNotEmpty();
        testAvgScoreInRange();
        testStudentIdNotEmpty();

        System.out.println("Пройдено: " + passed);
        System.out.println("Упало: " + failed);
    }

    private static void testSizeOfFill() {
        List<Student> result = new RandomFill().fill(5, null);
        if (result.size() == 5) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testSizeOfFill: ожидалось 5, получено " + result.size());
        }
    }

    private static void testGroupNumNotEmpty() {
        List<Student> students = new RandomFill().fill(50, null);
        boolean allValid = true;
        for (Student student : students) {
            if (student.getGroupNum() == null || student.getGroupNum().trim().isEmpty()) {
                allValid = false;
                break;
            }
        }
        if (allValid) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testGroupNumNotEmpty: найдена пустая группа");
        }
    }

    private static void testAvgScoreInRange() {
        List<Student> students = new RandomFill().fill(50, null);
        boolean allValid = true;
        for (Student student : students) {
            if (student.getAvgScore() < Student.MIN_SCORE || student.getAvgScore() > Student.MAX_SCORE) {
                allValid = false;
                break;
            }
        }
        if (allValid) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testAvgScoreInRange: найден балл вне диапазона");
        }
    }

    private static void testStudentIdNotEmpty() {
        List<Student> students = new RandomFill().fill(50, null);
        boolean allValid = true;
        for (Student student : students) {
            if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
                allValid = false;
                break;
            }
        }
        if (allValid) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testStudentIdNotEmpty: найден пустой id");
        }
    }
}