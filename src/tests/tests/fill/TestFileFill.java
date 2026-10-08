package tests.fill;

import fill.FileFill;
import student.Student;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class TestFileFill {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        InputStream originalIn = System.in;

        testExactLength();
        testMoreLinesThanLength();
        testLessLinesThanLength();
        testInvalidLinesSkipped();
        testNonExistentPath();
        testMissingField();

        System.setIn(originalIn);
        System.out.println("Пройдено: " + passed);
        System.out.println("Упало: " + failed);
    }

    private static void testExactLength() {
        File file = createTempFile(
                "А-01-23, 4.5, 12345",
                "А-02-23, 3.8, 67890",
                "А-03-23, 5.0, 11111"
        );
        setInput(file.getAbsolutePath() + "\n");

        List<Student> result = new FileFill().fill(3, new Scanner(System.in));

        check("testExactLength", 3, result.size());
    }

    private static void testMoreLinesThanLength() {
        File file = createTempFile(
                "А-01-23, 4.5, 12345",
                "А-02-23, 3.8, 67890",
                "А-03-23, 5.0, 11111",
                "А-04-23, 4.0, 22222",
                "А-05-23, 3.5, 33333"
        );
        setInput(file.getAbsolutePath() + "\n");

        List<Student> result = new FileFill().fill(3, new Scanner(System.in));

        check("testMoreLinesThanLength", 3, result.size());
    }

    private static void testLessLinesThanLength() {
        File fileShort = createTempFile(
                "А-01-23, 4.5, 12345"
        );
        File fileEnough = createTempFile(
                "А-01-23, 4.5, 12345",
                "А-02-23, 3.8, 67890",
                "А-03-23, 5.0, 11111"
        );
        setInput(fileShort.getAbsolutePath() + "\n" + fileEnough.getAbsolutePath() + "\n");

        List<Student> result = new FileFill().fill(3, new Scanner(System.in));

        check("testLessLinesThanLength", 3, result.size());
    }

    private static void testInvalidLinesSkipped() {
        File file = createTempFile(
                "А-01-23, 4.5, 12345",
                "битая строка",
                "А-02-23, 3.8, 67890",
                "А-03-23, abc, 11111",
                "А-04-23, 4.0, 22222"
        );
        setInput(file.getAbsolutePath() + "\n");

        List<Student> result = new FileFill().fill(3, new Scanner(System.in));

        check("testInvalidLinesSkipped", 3, result.size());
    }

    private static void testNonExistentPath() {
        File fileOk = createTempFile(
                "А-01-23, 4.5, 12345",
                "А-02-23, 3.8, 67890"
        );
        String badPath = "нет_такого_файла_" + System.nanoTime() + ".txt";
        setInput(badPath + "\n" + fileOk.getAbsolutePath() + "\n");

        List<Student> result = new FileFill().fill(2, new Scanner(System.in));

        check("testNonExistentPath", 2, result.size());
    }

    private static void testMissingField() {
        File file = createTempFile(
                "А-01-23, 4.5",
                "А-02-23, 3.8, 67890",
                "А-03-23, 5.0, 11111"
        );
        setInput(file.getAbsolutePath() + "\n");

        List<Student> result = new FileFill().fill(2, new Scanner(System.in));

        check("testMissingField", 2, result.size());
    }

    private static void check(String testName, int expected, int actual) {
        if (expected == actual) {
            passed++;
        } else {
            failed++;
            System.out.println("FAIL " + testName + ": ожидалось " + expected + ", получено " + actual);
        }
    }

    private static File createTempFile(String... lines) {
        try {
            File file = File.createTempFile("students_test", ".txt");
            file.deleteOnExit();
            try (FileWriter writer = new FileWriter(file)) {
                for (String line : lines) {
                    writer.write(line + System.lineSeparator());
                }
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать временный файл", e);
        }
    }

    private static void setInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
}