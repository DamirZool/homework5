package tests.fill;

import fill.ManualFill;
import student.Student;

import java.util.List;
import java.util.Scanner;

public class TestManualFill {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testTwoValidStudents();
        testSingleValidStudent();
        testEmptyGroupRetry();
        testEmptyStudentIdRetry();
        testNonNumericScoreRetry();
        testScoreTooHighRetry();
        testScoreTooLowRetry();
        testZeroLength();

        System.out.println();
        System.out.println("Пройдено: " + passed);
        System.out.println("Упало:    " + failed);
    }

    private static void testTwoValidStudents() {
        List<Student> result = run(
                "А-01-23\n12345\n4,5\nА-02-23\n67890\n3,8\n",
                2);

        check("testTwoValidStudents",
                result.size() == 2
                        && eq(result.get(0).getGroupNum(), "А-01-23")
                        && eq(result.get(0).getStudentId(), "12345")
                        && result.get(0).getAvgScore() == 4.5
                        && eq(result.get(1).getGroupNum(), "А-02-23")
                        && eq(result.get(1).getStudentId(), "67890")
                        && result.get(1).getAvgScore() == 3.8,
                result);
    }

    private static void testSingleValidStudent() {
        List<Student> result = run(
                "А-03-23\n55555\n5,0\n",
                1);

        check("testSingleValidStudent",
                result.size() == 1
                        && eq(result.get(0).getGroupNum(), "А-03-23")
                        && eq(result.get(0).getStudentId(), "55555")
                        && result.get(0).getAvgScore() == 5.0,
                result);
    }

    private static void testEmptyGroupRetry() {
        List<Student> result = run(
                "\n\nА-04-23\n44444\n4,0\n",
                1);

        check("testEmptyGroupRetry",
                result.size() == 1
                        && eq(result.get(0).getGroupNum(), "А-04-23")
                        && eq(result.get(0).getStudentId(), "44444")
                        && result.get(0).getAvgScore() == 4.0,
                result);
    }

    private static void testEmptyStudentIdRetry() {
        List<Student> result = run(
                "А-05-23\n\n33333\n3,5\n",
                1);

        check("testEmptyStudentIdRetry",
                result.size() == 1
                        && eq(result.get(0).getGroupNum(), "А-05-23")
                        && eq(result.get(0).getStudentId(), "33333")
                        && result.get(0).getAvgScore() == 3.5,
                result);
    }

    private static void testNonNumericScoreRetry() {
        List<Student> result = run(
                "А-06-23\n22222\nabc\n4,2\n",
                1);

        check("testNonNumericScoreRetry",
                result.size() == 1
                        && eq(result.get(0).getStudentId(), "22222")
                        && result.get(0).getAvgScore() == 4.2,
                result);
    }

    private static void testScoreTooHighRetry() {
        List<Student> result = run(
                "А-07-23\n11111\n150\n4,8\n",
                1);

        check("testScoreTooHighRetry",
                result.size() == 1
                        && eq(result.get(0).getStudentId(), "11111")
                        && result.get(0).getAvgScore() == 4.8,
                result);
    }

    private static void testScoreTooLowRetry() {
        List<Student> result = run(
                "А-08-23\n99999\n-5\n3,3\n",
                1);

        check("testScoreTooLowRetry",
                result.size() == 1
                        && eq(result.get(0).getStudentId(), "99999")
                        && result.get(0).getAvgScore() == 3.3,
                result);
    }

    private static void testZeroLength() {
        List<Student> result = run("", 0);

        check("testZeroLength",
                result.isEmpty(),
                result);
    }

    private static List<Student> run(String input, int length) {
        Scanner scanner = new Scanner(input);
        return new ManualFill().fill(length, scanner);
    }

    private static boolean eq(String a, String b) {
        return a != null && a.equals(b);
    }

    private static void check(String name, boolean condition, Object actual) {
        if (condition) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL " + name + ": получено " + actual);
        }
    }
}