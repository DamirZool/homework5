package tests.fill;

import fill.ManualFill;
import student.Student;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class TestManualFill {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        InputStream originalIn = System.in;

        testCorrectInput();
        testEmptyGroup();
        testNonNumericScore();
        testScoreOutOfRange();
        testEmptyStudentId();
        testZeroLength();

        System.setIn(originalIn);
        System.out.println("Пройдено: " + passed);
        System.out.println("Упало: " + failed);
    }

    private static void testCorrectInput() {
        setInput("ИКБО-01-23\n12345\n4.5\nИКБО-02-23\n67890\n3.8\n");

        List<Student> result = new ManualFill().fill(2, new Scanner(System.in));

        if (result.size() == 2
                && result.get(0).getGroupNum().equals("ИКБО-01-23")
                && result.get(0).getStudentId().equals("12345")
                && result.get(0).getAvgScore() == 4.5
                && result.get(1).getGroupNum().equals("ИКБО-02-23")
                && result.get(1).getStudentId().equals("67890")
                && result.get(1).getAvgScore() == 3.8) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testCorrectInput: получено " + result);
        }
    }

    private static void testEmptyGroup() {
        setInput("\n\nИКБО-01-23\n12345\n4.5\n");

        List<Student> result = new ManualFill().fill(1, new Scanner(System.in));

        if (result.size() == 1 && result.get(0).getGroupNum().equals("ИКБО-01-23")) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testEmptyGroup: получено " + result);
        }
    }

    private static void testNonNumericScore() {
        setInput("ИКБО-01-23\n12345\nabc\n4.5\n");

        List<Student> result = new ManualFill().fill(1, new Scanner(System.in));

        if (result.size() == 1 && result.get(0).getAvgScore() == 4.5) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testNonNumericScore: получено " + result);
        }
    }

    private static void testScoreOutOfRange() {
        setInput("ИКБО-01-23\n12345\n150\n4.5\n");

        List<Student> result = new ManualFill().fill(1, new Scanner(System.in));

        if (result.size() == 1 && result.get(0).getAvgScore() == 4.5) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testScoreOutOfRange: получено " + result);
        }
    }

    private static void testEmptyStudentId() {
        setInput("ИКБО-01-23\n\n12345\n4.5\n");

        List<Student> result = new ManualFill().fill(1, new Scanner(System.in));

        if (result.size() == 1 && result.get(0).getStudentId().equals("12345")) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testEmptyStudentId: получено " + result);
        }
    }

    private static void testZeroLength() {
        setInput("");

        List<Student> result = new ManualFill().fill(0, new Scanner(System.in));

        if (result.isEmpty()) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL testZeroLength: ожидался пустой список, получено " + result.size());
        }
    }

    private static void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
}