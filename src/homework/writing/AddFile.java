package writing;

import student.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

public class AddFile {
    private static final Set<String> RESERVED_NAMES = new HashSet<>(Arrays.asList(
            "CON", "PRN", "AUX", "NUL",
            "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9",
            "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"
    ));
    private static final Pattern INVALID_CHARS = Pattern.compile("[<>:\"/\\\\|?*\\x00-\\x1F]");

    public static void addFile(List<Student> students, Scanner scanner) {
        while (true) {
            System.out.print("Введите имя файла (0 — отмена): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                System.out.println("Запись отменена.");
                return;
            }

            String fileName = validateName(input);
            if (fileName == null) {
                System.out.println("Попробуйте снова.");
                continue;
            }

            doFile(students, fileName);
            System.out.println("Файл сохранён.");
            return;
        }
    }

    private static String validateName(String fileName) {
        if (fileName == null || fileName.isEmpty()) return null;
        if (fileName.length() > 255) return null;

        char lastChar = fileName.charAt(fileName.length() - 1);
        if (lastChar == '.' || lastChar == ' ') return null;

        if (INVALID_CHARS.matcher(fileName).find()) return null;

        String nameWithoutExtension = fileName.split("\\.")[0].toUpperCase();
        if (RESERVED_NAMES.contains(nameWithoutExtension)) return null;

        return fileName;
    }

    private static void doFile(List<Student> students, String fileName) {
        String path = fileName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(path, StandardCharsets.UTF_8, true))) {
            for (Student s : students) {
                writer.write(s.toString());
                writer.newLine();
            }
            System.out.println("Записано в " + path);
        } catch (IOException e) {
            System.out.println("Ошибка записи: " + e.getMessage());
        }
    }
}